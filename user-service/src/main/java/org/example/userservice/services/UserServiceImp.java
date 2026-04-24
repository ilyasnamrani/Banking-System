package org.example.userservice.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.userservice.dtos.UserRequest;
import org.example.userservice.dtos.UserResponse;
import org.example.userservice.dtos.UserResponseV2;
import org.example.userservice.entities.User;
import org.example.userservice.mappers.UserMapper;
import org.example.userservice.repo.UserRepository;
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
    public UserResponseV2 getFeignClientUser(Long userId, Jwt jwt) throws AccessDeniedException {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User Not Found")
        );
        if(!jwt.getSubject().equals(user.getKeycloakId())){
            throw new AccessDeniedException("You cannot access to another account");
        }
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
    public UserResponse createNewUser(UserRequest userRequest) {
        User user = userMapper.toUser(userRequest);
        user.setKeycloakId(keycloakService.createUser(user.getEmail(), user.getPassword(),  user.getFirstName(), user.getLastName()));
        userRepository.save(user);
        //kafkaService.sendCandidateCreatedEvent(response);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest, Long idUser,Jwt jwt) {

        User updated = userRepository.findById(idUser)
                .map(existing -> {

                    if (userRequest.getFirstName() != null && !userRequest.getFirstName().isBlank()) {
                        existing.setFirstName(userRequest.getFirstName());
                    }

                    if (userRequest.getLastName() != null && !userRequest.getLastName().isBlank()) {
                        existing.setLastName(userRequest.getLastName());
                    }

                    if (userRequest.getEmail() != null && !userRequest.getEmail().isBlank()) {
                        existing.setEmail(userRequest.getEmail());
                    }

                    if (userRequest.getPassword() != null && !userRequest.getPassword().isBlank()) {
                        existing.setPassword(userRequest.getPassword());
                    }

                    if (userRequest.getCin() != null && !userRequest.getCin().isBlank()) {
                        existing.setCin(userRequest.getCin());
                    }

                    if (userRequest.getPhoneNumber() != null && !userRequest.getPhoneNumber().isBlank()) {
                        existing.setPhoneNumber(userRequest.getPhoneNumber());
                    }

                    keycloakService.updateUser(
                            existing.getKeycloakId(),
                            existing.getEmail(),
                            existing.getFirstName(),
                            existing.getLastName(),
                            existing.getPassword()
                    );
                    if(!existing.getKeycloakId().equals(jwt.getSubject())){
                        System.out.println("You cannot Update Another User !");
                    }
                    return userRepository.save(existing);
                })
                .orElseThrow(() -> new EntityNotFoundException("User Not Found with Id : " + idUser));
        UserResponse response =  userMapper.toUserResponse(updated);
//        kafkaService.sendCandidateUpdatedEvent(response);
        return response;
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
//        kafkaService.sendCandidateDeletedEvent(userId);
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
       return  userMapper.toUserResponse(user);
    }
}
