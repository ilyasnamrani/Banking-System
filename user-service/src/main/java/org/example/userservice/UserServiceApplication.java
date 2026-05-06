package org.example.userservice;

import org.example.userservice.dtos.UserRequest;
import org.example.userservice.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableDiscoveryClient
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    // @Bean
    // CommandLineRunner commandLineRunner(UserService userService) {
    //     return args -> {
    //         if (userService.getAllUsers().isEmpty()) {
    //             userService.createNewUser(new UserRequest("Ilyas", "Namrani", "ilyas@example.com", "password123", "0600000001", "AB123456"));
    //             userService.createNewUser(new UserRequest("John", "Doe", "john@example.com", "password123", "0600000002", "CD123456"));
    //             userService.createNewUser(new UserRequest("Jane", "Smith", "jane@example.com", "password123", "0600000003", "EF123456"));
    //             userService.createNewUser(new UserRequest("Alice", "Wonder", "alice@example.com", "password123", "0600000004", "GH123456"));
    //         }
    //     };
    // }

}
