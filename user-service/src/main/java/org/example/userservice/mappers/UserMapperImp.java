package org.example.userservice.mappers;

import org.example.userservice.dtos.UserRequest;
import org.example.userservice.dtos.UserResponse;
import org.example.userservice.dtos.UserResponseV2;
import org.example.userservice.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImp implements UserMapper {
    @Override
    public UserResponse toUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setIdUser(user.getIdUser());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setKeycloakId(user.getKeycloakId());
        return response;
    }

    @Override
    public UserResponseV2 toUserResponseV2(User user) {
        UserResponseV2 response = new UserResponseV2();
        response.setIdUser(user.getIdUser());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setKeycloakId(user.getKeycloakId());
        return response;
    }


    @Override
    public User toUser(UserRequest userRequest) {
        User user = new User() ;
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setCin(userRequest.getCin());
        return user;
    }
}
