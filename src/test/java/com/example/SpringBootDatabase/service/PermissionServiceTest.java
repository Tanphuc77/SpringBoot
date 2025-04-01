package com.example.SpringBootDatabase.service;

import com.example.SpringBootDatabase.dto.request.PermissionRequest;
import com.example.SpringBootDatabase.dto.response.PermissionResponse;
import com.example.SpringBootDatabase.entity.Permission;
import com.example.SpringBootDatabase.mapper.PermissionMapper;
import com.example.SpringBootDatabase.repository.PermissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class PermissionServiceTest {

    @Mock
    private PermissionRepository permissionRepository;
    @Mock
    private PermissionMapper permissionMapper;
    @InjectMocks
    private PermissionService permissionService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testCreatePermission() {
        Permission permission = new Permission();
        permission.setName("name");

        PermissionRequest request = new PermissionRequest();
        request.setName("name");

        PermissionResponse response = new PermissionResponse();
        response.setName("name");

        when(permissionMapper.toPermission(request)).thenReturn(permission);
        when(permissionRepository.save(permission)).thenReturn(permission);
        when(permissionMapper.toPermissionResponse(permission)).thenReturn(response);

        PermissionResponse result = permissionService.createPermission(request);
        assertEquals(response, result);

    }

    @Test
    void testGetAll() {
        Permission permission = new Permission();
        permission.setName("name");

        PermissionResponse response = new PermissionResponse();
        response.setName("name");

        when(permissionRepository.findAll()).thenReturn(java.util.List.of(permission));
        when(permissionMapper.toPermissionResponse(permission)).thenReturn(response);

        var result = permissionService.getAll();
        assertEquals(java.util.List.of(response), result);
    }

    @Test
    void testDelete() {
        permissionService.delete("permission");
    }
}