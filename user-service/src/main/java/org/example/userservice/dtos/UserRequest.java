package org.example.userservice.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 50)
    private String firstName;
    
    @NotBlank(message = "Le nom est obligatoire")
    private String lastName;
    
    @Email(message = "Email invalide")
    @NotBlank(message = "Email obligatoire")
    private String email;
    
    @NotBlank(message = "Mot de passe obligatoire")
    @Size(min = 8, message = "Mot de passe trop court")
    private String password;
    
    @NotBlank(message = "Numéro de téléphone obligatoire")
    @Size(min = 10, max = 10)
    private String phoneNumber;
    
    @NotBlank(message = "CIN obligatoire")
    @Size(min = 8, max = 8)
    private String cin;

}

