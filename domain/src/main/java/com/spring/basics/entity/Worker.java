package com.spring.basics.entity;

import javassist.bytecode.ByteArray;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String cpf;
    private String cep;
    private String city;
    @Enumerated(EnumType.STRING)
    private PlanEnum plan;
    private String latitude;
    private String longitude;
    private String formattedAddress;
    private String description;
    private Double rating;
    private Boolean isActive;
    private Long remainingContacts;
    private Long revealedContacts;
    private LocalDateTime renewalDate;
    private byte[] documentPhoto;
    private LocalDateTime createdAt;


}
