package org.example.notificationservice.entitie;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity @AllArgsConstructor
@NoArgsConstructor @ToString
@Getter @Setter
public class Notification {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idNotification ;
    private String title;
    private String message;

}
