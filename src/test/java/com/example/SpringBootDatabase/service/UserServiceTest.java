package com.example.SpringBootDatabase.service;

import com.example.SpringBootDatabase.dto.request.UserRequest;
import com.example.SpringBootDatabase.dto.request.UserUpdateRequest;
import com.example.SpringBootDatabase.dto.response.UserResponse;
import com.example.SpringBootDatabase.entity.User;
import com.example.SpringBootDatabase.exception.AppException;
import com.example.SpringBootDatabase.mapper.UserMapper;
import com.example.SpringBootDatabase.repository.RoleRepository;
import com.example.SpringBootDatabase.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }


    @Test
    void testGetAllUsers() {
        User user = new User();
        user.setUserId("test");
        user.setUsername("test");
        user.setPassword("test");

        UserResponse userResponse = new UserResponse();
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        List<UserResponse> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals(userResponse, result.get(0));
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setUserId("test");

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId("test");

        when(userRepository.findById("test")).thenReturn(java.util.Optional.of(user));
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.getUserById("test");
        assertEquals(userResponse, result);

    }

    @Test
    void testAddUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("test");
        userRequest.setEmail("test");
        userRequest.setPassword("password123");

        User user = new User();
        user.setUserId("test");
        user.setUsername("test");
        user.setEmail("test");

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId("test");
        userResponse.setUsername("test");
        userResponse.setEmail("test");

        when(userRepository.existsByUsername("test")).thenReturn(false);
        when(userRepository.existsByEmail("test")).thenReturn(false);
        when(userMapper.toUser(userRequest)).thenReturn(user);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.addUser(userRequest);

        assertEquals(userResponse, result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testAddUserWithExistingUsername() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("test");
        userRequest.setEmail("test");
        userRequest.setPassword("password123");

        when(userRepository.existsByUsername("test")).thenReturn(true);

        try {
            userService.addUser(userRequest);
        } catch (Exception e) {
            assertEquals("User existed", e.getMessage());
        }
    }

    @Test
    void testAddUserWithExistingEmail() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("test");
        userRequest.setEmail("test");
        userRequest.setPassword("password123");

        when(userRepository.existsByUsername("test")).thenReturn(false);
        when(userRepository.existsByEmail("test")).thenReturn(true);

        try {
            userService.addUser(userRequest);
        } catch (Exception e) {
            assertEquals("User email already exists", e.getMessage());
        }
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setUserId("test");
        user.setUsername("test");
        user.setEmail("test");

        UserUpdateRequest userRequest = new UserUpdateRequest();
        userRequest.setUsername("test");
        userRequest.setEmail("test");
        userRequest.setPassword("password123");

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId("test");
        userResponse.setUsername("test");
        userResponse.setEmail("test");

        when(userRepository.findById("test")).thenReturn(java.util.Optional.of(user));
        when(userRepository.existsByEmail("test")).thenReturn(false);
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(roleRepository.findAllById(any())).thenReturn(List.of());

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.updateUser("test", userRequest);
        assertEquals(userResponse, result);
        verify(userRepository).save(any(User.class));

    }

    @Test
    void testDeleteUser() {
        String userId = "test";

        User user = new User();
        user.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);
    }

    @Test
    void testSearchUsers() {
        User user = new User();
        user.setUserId("test");
        user.setName("test");
        user.setEmail("test");

        List<User> users = List.of(user);

        when(userRepository.findByEmailOrName("test", "%test%")).thenReturn(users);
        List<User> result = userService.searchUsers("test", "test");

        assertEquals(users, result);
    }

    @Test
    void testSearchUsers_NotFound() {
        when(userRepository.findByEmailOrName("notfound", "%notfound%")).thenReturn(List.of());

        Exception exception = assertThrows(AppException.class, () -> userService.searchUsers("notfound", "notfound"));
        assertEquals("No students found with email or name", exception.getMessage());
    }

}