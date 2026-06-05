package org.example.accountservice.dtos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public class AccountRequest {

        @NotNull(message = "idUser est obligatoire")
        private Long idUser ;
        @NotBlank(message = "Le type de compte est obligatoire")
        private String type ;

    }

