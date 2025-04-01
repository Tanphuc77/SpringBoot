package com.example.SpringBootDatabase.controller;

import com.example.SpringBootDatabase.dto.request.RoleRequest;
import com.example.SpringBootDatabase.dto.response.ApiResponse;
import com.example.SpringBootDatabase.dto.response.RoleResponse;
import com.example.SpringBootDatabase.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {

    RoleService roleService;

    @GetMapping
    ApiResponse<List<RoleResponse>> getAllRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @PostMapping
    ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.createRole(roleRequest))
                .build();
    }

    @GetMapping("/{roleName}")
    ApiResponse<RoleResponse> getRole(String roleName) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.getRole(roleName))
                .build();
    }

    @DeleteMapping("/{roleName}")
    ApiResponse<Void> deleteRole(String roleName) {
        roleService.deleteRole(roleName);
        return ApiResponse.<Void>builder().build();
    }

}
