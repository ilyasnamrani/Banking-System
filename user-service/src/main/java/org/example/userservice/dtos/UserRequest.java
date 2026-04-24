package org.example.userservice.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String password;
    @Size(min = 10, max = 10)
    private String phoneNumber;
    @Size(min = 8, max = 8)
    private String cin;

}

