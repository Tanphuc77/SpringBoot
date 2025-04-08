package com.example.SpringBootDatabase.controller;

import com.example.SpringBootDatabase.dto.request.UserRequest;
import com.example.SpringBootDatabase.dto.request.UserUpdateRequest;
import com.example.SpringBootDatabase.dto.response.ApiResponse;
import com.example.SpringBootDatabase.dto.response.PageResponse;
import com.example.SpringBootDatabase.dto.response.UserDetailResponse;
import com.example.SpringBootDatabase.dto.response.UserResponse;
import com.example.SpringBootDatabase.entity.User;
import com.example.SpringBootDatabase.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    ApiResponse<PageResponse<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        PageResponse<UserResponse> pageResponse = userService.getAllUsers(page, size);
        return ApiResponse.<PageResponse<UserResponse>>builder()
                .code(200)
                .message("Success")
                .result(pageResponse)
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserDetailResponse> getStudentById(@PathVariable String userId) {
        ApiResponse<UserDetailResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getUserById(userId));
        return apiResponse;
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PostMapping
    ApiResponse<UserResponse> addStudent(@Valid @RequestBody UserRequest userRequest) {
        log.info("Received request: {}", userRequest);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.addUser(userRequest));
        return apiResponse;
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateStudent(@Valid @PathVariable String userId, @RequestBody UserUpdateRequest userRequest) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.updateUser(userId, userRequest));
        return apiResponse;
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteStudent(@PathVariable String userId) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        userService.deleteUser(userId);
        return apiResponse;
    }

    @GetMapping("/search")
    ApiResponse<List<User>> searchStudents(
            @RequestParam(required = false, defaultValue = "") String email,
            @RequestParam(required = false, defaultValue = "") String name) {
        ApiResponse<List<User>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.searchUsers(email, name));
        return apiResponse;
    }

}
