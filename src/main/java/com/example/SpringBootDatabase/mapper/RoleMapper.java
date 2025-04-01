package com.example.SpringBootDatabase.mapper;

import com.example.SpringBootDatabase.dto.request.RoleRequest;
import com.example.SpringBootDatabase.dto.response.RoleResponse;
import com.example.SpringBootDatabase.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
