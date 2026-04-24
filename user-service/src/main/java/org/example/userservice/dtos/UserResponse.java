package org.example.userservice.dtos;

import lombok.*;

@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long idUser;
    private String firstName;
    private String lastName;
    private String email;
    private String keycloakId;
    private String phoneNumber;
    private String eventType ;

}
