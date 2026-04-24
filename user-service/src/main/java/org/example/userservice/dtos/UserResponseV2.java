package org.example.userservice.dtos;


import lombok.*;

@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseV2 {
    private Long idUser;
    private String firstName;
    private String lastName;
    private String keycloakId;


}
