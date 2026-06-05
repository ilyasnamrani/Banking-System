package org.example.userservice.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.userservice.dtos.UserRequest;
import org.example.userservice.dtos.UserResponse;
import org.example.userservice.dtos.UserResponseV2;
import org.example.userservice.entities.User;
import org.example.userservice.mappers.UserMapper;
import org.example.userservice.repo.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService  {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final KafkaService kafkaService;
    private final KeycloakService keycloakService;
    public UserServiceImp(UserRepository userRepository,
                          KeycloakService keycloakService,
                          KafkaService kafkaService,
                          UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.keycloakService = keycloakService;
        this.kafkaService = kafkaService;
    }

  @Override
  public Long getIdUserByKeycloakId(String keycloakId) {
        User user = userRepository.findByKeycloakId(keycloakId);
        if (user == null) {
            throw new EntityNotFoundException("User not found in local database for Keycloak ID: " + keycloakId);
        }
        return user.getIdUser();
  }
    @Override
    public UserResponse getUser(Long userId, Jwt jwt) throws AccessDeniedException {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User Not Found")
        );
        if(!jwt.getSubject().equals(user.getKeycloakId())){
            throw new AccessDeniedException("You cannot access to another account");
        }
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponseV2 getFeignClientUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User Not Found")
        );
        return userMapper.toUserResponseV2(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> responses = new ArrayList<>();
        for (User user : users) {
            responses.add(userMapper.toUserResponse(user));
        }
        return responses;
    }

    @Override
    public List<UserResponseV2> getAllFeignClientUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseV2> responses = new ArrayList<>();
        for (User user : users) {
            responses.add(userMapper.toUserResponseV2(user));
        }
        return responses;
    }


    @Override
    @Transactional
    public UserResponse createNewUser(UserRequest userRequest) {
        User user = userMapper.toUser(userRequest);
        user.setKeycloakId(keycloakService.createUser(user.getEmail(), userRequest.getPassword(),  user.getFirstName(), user.getLastName()));
         UserResponse response = userMapper.toUserResponse(userRepository.save(user));
        kafkaService.sendCandidateCreatedEvent(response);
        return response;
    }

    @Override
    @Transactional
    public UserResponse updateUser(UserRequest userRequest, Long idUser, Jwt jwt) {

        User updated = userRepository.findById(idUser)
                .map(existing -> {

                    if (!existing.getKeycloakId().equals(jwt.getSubject())) {
                        try {
                            throw new AccessDeniedException("You cannot update another user!");
                        } catch (AccessDeniedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if (userRequest.getFirstName() != null && !userRequest.getFirstName().isBlank()) {
                        existing.setFirstName(userRequest.getFirstName());
                    }

                    if (userRequest.getLastName() != null && !userRequest.getLastName().isBlank()) {
                        existing.setLastName(userRequest.getLastName());
                    }

                    if (userRequest.getEmail() != null && !userRequest.getEmail().isBlank()) {
                        existing.setEmail(userRequest.getEmail());
                    }


                    if (userRequest.getCin() != null && !userRequest.getCin().isBlank()) {
                        existing.setCin(userRequest.getCin());
                    }

                    if (userRequest.getPhoneNumber() != null && !userRequest.getPhoneNumber().isBlank()) {
                        existing.setPhoneNumber(userRequest.getPhoneNumber());
                    }
                    User savedUser = userRepository.save(existing);
                    keycloakService.updateUser(
                            savedUser.getKeycloakId(),
                            savedUser.getEmail(),
                            savedUser.getFirstName(),
                            savedUser.getLastName(),
                            userRequest.getPassword()
                    );
                    kafkaService.sendCandidateUpdatedEvent(userMapper.toUserResponse(savedUser));
                    return savedUser;
                })
                .orElseThrow(() -> new EntityNotFoundException("User Not Found with Id : " + idUser));

        return userMapper.toUserResponse(updated);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
        kafkaService.sendCandidateDeletedEvent(userId);
    }


    @Override
    public UserResponse getUserByCin(String cin , Jwt jwt) throws AccessDeniedException {
        User user =  userRepository.findByCin(cin) ;
        if(user == null){
            throw new EntityNotFoundException("User Not Found");
        }

        if(!user.getKeycloakId().equals(jwt.getSubject())){
            throw new AccessDeniedException("You don't have the access to other users");
        }
         return userMapper.toUserResponse(user);
    }

}
