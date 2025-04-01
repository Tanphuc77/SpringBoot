package com.example.SpringBootDatabase.mapper;

import com.example.SpringBootDatabase.dto.request.PermissionRequest;
import com.example.SpringBootDatabase.dto.response.PermissionResponse;
import com.example.SpringBootDatabase.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
