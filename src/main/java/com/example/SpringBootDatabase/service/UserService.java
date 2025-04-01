package com.example.SpringBootDatabase.service;

import com.example.SpringBootDatabase.constant.PredefinedRole;
import com.example.SpringBootDatabase.dto.request.UserRequest;
import com.example.SpringBootDatabase.dto.request.UserUpdateRequest;
import com.example.SpringBootDatabase.dto.response.UserResponse;
import com.example.SpringBootDatabase.entity.User;
import com.example.SpringBootDatabase.entity.Role;
import com.example.SpringBootDatabase.exception.AppException;
import com.example.SpringBootDatabase.exception.Errorcode;
import com.example.SpringBootDatabase.mapper.UserMapper;
import com.example.SpringBootDatabase.repository.RoleRepository;
import com.example.SpringBootDatabase.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    RedisTemplate<String, Object> redisTemplate;

    @PreAuthorize("hasAuthority('Read_Data')")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("hasAuthority('Read_Data') or returnObject.username == authentication.name")
    public UserResponse getUserById(String userId) {
        String cacheKey = "user:" + userId;
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();

        if (redisTemplate.hasKey(cacheKey)) {
            log.info("Fetching user from Redis cache: {}", userId);
            return (UserResponse) valueOps.get(cacheKey);
        }

        // Nếu không có, lấy từ DB và lưu vào Redis
        log.info("Fetching user from database: {}", userId);
        UserResponse userResponse = userMapper.toUserResponse(
                userRepository.findById(userId)
                        .orElseThrow(() -> new AppException(Errorcode.USER_NOT_FOUND))
        );

        valueOps.set(cacheKey, userResponse, 10, TimeUnit.MINUTES);
        return userResponse;
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(Errorcode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasAuthority('Create_Data')")
    public UserResponse addUser(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new AppException(Errorcode.USER_EXISTED);
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new AppException(Errorcode.USER_EMAIL_ALREADY_EXISTS);
        }
        User user = userMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        user.setRoles(roles);

        UserResponse savedUser = userMapper.toUserResponse(userRepository.save(user));

        // Lưu vào Redis
        String cacheKey = "user:" + savedUser.getUserId();
        redisTemplate.opsForValue().set(cacheKey, savedUser, 10, TimeUnit.MINUTES);
        log.info("User saved to Redis: {}", savedUser.getUserId());

        return savedUser;
    }

    @PreAuthorize("hasAuthority('Update_Data')")
    public UserResponse updateUser(String userId, UserUpdateRequest userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(Errorcode.USER_NOT_FOUND));

        if (!user.getEmail().equals(userRequest.getEmail()) && userRepository.existsByEmail(userRequest.getEmail())) {
            throw new AppException(Errorcode.USER_EMAIL_ALREADY_EXISTS);
        }
        userMapper.updateUser(user, userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        var roles = roleRepository.findAllById(userRequest.getRoles());
        user.setRoles(new HashSet<>(roles));

        UserResponse updatedUser = userMapper.toUserResponse(userRepository.save(user));

        // Cập nhật Redis
        String cacheKey = "user:" + userId;
        redisTemplate.opsForValue().set(cacheKey, updatedUser, 10, TimeUnit.MINUTES);
        log.info("User updated in Redis: {}", userId);

        return updatedUser;
    }

    @PreAuthorize("hasAuthority('Delete_Data')")
    public void deleteUser(String userId) {
        userRepository.findById(userId).orElseThrow(() -> new AppException(Errorcode.USER_NOT_FOUND));
        userRepository.deleteById(userId);

        // Xóa cache trên Redis
        String cacheKey = "user:" + userId;
        redisTemplate.delete(cacheKey);
        log.info("User deleted from Redis: {}", userId);
    }

    @PreAuthorize("hasAuthority('Read_Data')")
    public List<User> searchUsers(String email, String name) {
        List<User> users = userRepository.findByEmailOrName(email, "%" + name + "%");
        if (users.isEmpty()) {
            throw new AppException(Errorcode.STUDENT_NOT_FOUND_EMAIL_OR_NAME);
        }
        return users;
    }
}
