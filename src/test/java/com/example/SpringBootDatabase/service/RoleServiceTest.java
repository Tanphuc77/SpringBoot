package com.example.SpringBootDatabase.service;

import com.example.SpringBootDatabase.dto.request.RoleRequest;
import com.example.SpringBootDatabase.dto.response.RoleResponse;
import com.example.SpringBootDatabase.entity.Role;
import com.example.SpringBootDatabase.mapper.RoleMapper;
import com.example.SpringBootDatabase.repository.PermissionRepository;
import com.example.SpringBootDatabase.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class RoleServiceTest {

    @Mock
    private PermissionRepository permissionRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }


    @Test
    void testGetAll() {
        Role role = new Role();
        role.setName("test");

        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setName("test");

        when(roleRepository.findAll()).thenReturn(List.of(role));
        when(roleMapper.toRoleResponse(role)).thenReturn(roleResponse);

        List<RoleResponse> result = roleService.getAll();
        assertEquals(1, result.size(), "List size should be 1");
        assertEquals("test", result.get(0).getName(), "Role name should be 'test'");

    }

    @Test
    void testCreateRole() {
        Role role = new Role();
        role.setName("test");

        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setName("test");

        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setName("test");

        when(roleMapper.toRole(any())).thenReturn(role);
        when(permissionRepository.findAllById(any())).thenReturn(List.of());
        when(roleRepository.save(any())).thenReturn(role);
        when(roleMapper.toRoleResponse(role)).thenReturn(roleResponse);

        RoleResponse result = roleService.createRole(roleRequest);
        assertEquals("test", result.getName(), "Role name should be 'test'");
    }

    @Test
    void testGetRole() {
        Role role = new Role();
        role.setName("test");

        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setName("test");

        when(roleRepository.findById("test")).thenReturn(java.util.Optional.of(role));
        when(roleMapper.toRoleResponse(role)).thenReturn(roleResponse);

        RoleResponse result = roleService.getRole("test");
        assertEquals("test", result.getName(), "Role name should be 'test'");
    }

    @Test
    void testDeleteRole() {
        roleService.deleteRole("test");
    }
}