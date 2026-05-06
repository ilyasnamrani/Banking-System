package org.example.accountservice.web;

import org.example.accountservice.Models.UserResponseV2;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.file.AccessDeniedException;
import java.util.List;

@FeignClient(name = "user-service")
public interface UserFeignClient {
    @GetMapping("/users/feignClients")
    public List<UserResponseV2> getAllFeignClientUsers();

    @GetMapping("/users/feignClient/{id}")
    public UserResponseV2 getFeignClientUser(@PathVariable Long id);

}
