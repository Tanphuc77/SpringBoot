package com.example.SpringBootDatabase.service;

import com.example.SpringBootDatabase.dto.request.RoleRequest;
import com.example.SpringBootDatabase.dto.response.RoleResponse;
import com.example.SpringBootDatabase.entity.Role;
import com.example.SpringBootDatabase.exception.AppException;
import com.example.SpringBootDatabase.exception.Errorcode;
import com.example.SpringBootDatabase.mapper.RoleMapper;
import com.example.SpringBootDatabase.repository.PermissionRepository;
import com.example.SpringBootDatabase.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {

    PermissionRepository permissionRepository;

    RoleRepository roleRepository;

    RoleMapper roleMapper;

    public List<RoleResponse> getAll() {
        var roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }

    public RoleResponse createRole(RoleRequest roleRequest) {
        Role role = roleMapper.toRole(roleRequest);

        var permissions = permissionRepository.findAllById(roleRequest.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public RoleResponse getRole(String roleName) {
        Role role = roleRepository.findById(roleName).orElseThrow(() -> new AppException(Errorcode.ROLE_NOT_FOUND));
        return roleMapper.toRoleResponse(role);
    }

    public void deleteRole(String roleName) {
        roleRepository.deleteById(roleName);
    }

}
