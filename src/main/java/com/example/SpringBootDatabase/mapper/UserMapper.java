package com.example.SpringBootDatabase.mapper;

import com.example.SpringBootDatabase.dto.request.UserRequest;
import com.example.SpringBootDatabase.dto.request.UserUpdateRequest;
import com.example.SpringBootDatabase.dto.response.UserResponse;
import com.example.SpringBootDatabase.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequest userRequest);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest userRequest);

}
