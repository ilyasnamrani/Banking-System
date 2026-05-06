package org.example.userservice.web;


import org.example.userservice.dtos.UserRequest;
import org.example.userservice.dtos.UserResponse;
import org.example.userservice.dtos.UserResponseV2;
import org.example.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id,@AuthenticationPrincipal Jwt jwt) throws AccessDeniedException {
        return userService.getUser(id,jwt);
    }

    @GetMapping("/feignClient/{id}")
    public UserResponseV2 getFeignClientUser(@PathVariable Long id){
        return userService.getFeignClientUser(id);
    }

    @GetMapping("/all")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/feignClients")
    public List<UserResponseV2> getAllFeignClientUsers() {
        return userService.getAllFeignClientUsers();
    }

    @PostMapping("/create")
    public UserResponse createUser(@RequestBody @jakarta.validation.Valid UserRequest userRequest) {
        return userService.createNewUser(userRequest);
    }

    @PutMapping("/update/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody @jakarta.validation.Valid UserRequest userRequest ,@AuthenticationPrincipal Jwt  jwt) {
        return userService.updateUser(userRequest, id,jwt);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

    }

    @GetMapping("/cin")
    public UserResponse getUserByCin(@RequestParam String cin ,@AuthenticationPrincipal Jwt jwt) throws AccessDeniedException {
       return  userService.getUserByCin(cin,jwt);
    }

}
