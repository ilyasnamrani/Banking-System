package org.example.userservice.services;

import org.example.userservice.dtos.UserRequest;
import org.example.userservice.dtos.UserResponse;
import org.example.userservice.dtos.UserResponseV2;
import org.springframework.security.oauth2.jwt.Jwt;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface UserService {
    public UserResponse getUser(Long userId, Jwt jwt) throws AccessDeniedException;
    public UserResponseV2 getFeignClientUser(Long userId);
    public List<UserResponse> getAllUsers();
    public List<UserResponseV2> getAllFeignClientUsers();
    public UserResponse createNewUser(UserRequest userRequest);
    public UserResponse updateUser(UserRequest userRequest  ,Long idUser, Jwt jwt);
    public void deleteUser(Long userId);
    public UserResponse getUserByCin(String cin, Jwt jwt) throws AccessDeniedException;
}
