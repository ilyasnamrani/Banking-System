package org.example.userservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity @Getter @ToString @Builder
@Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idUser ;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String keycloakId ;
    @Size(min = 10, max = 10)
    private String phoneNumber;
    @Size(min = 8, max = 8)
    private String cin;
//    private String eventType ;

}
