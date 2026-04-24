package org.example.userservice.mappers;

import org.example.userservice.dtos.UserRequest;
import org.example.userservice.dtos.UserResponse;
import org.example.userservice.dtos.UserResponseV2;
import org.example.userservice.entities.User;

public interface UserMapper {
    public UserResponse toUserResponse(User user);
    public UserResponseV2 toUserResponseV2(User user);
    public User toUser(UserRequest user);
}
