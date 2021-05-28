package com.spring.basics.api.response;

import com.spring.basics.entity.ActuationArea;
import com.spring.basics.entity.PlanEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateWorkerResponse {

    private Long id;
    private String name;
    private String cpf;
    private String phoneNumber;
    private String email;
    private PlanEnum plan;
    private Long remainingContacts;
    private Long revealedContacts;
    private LocalDateTime renewalDate;
    private String city;
    private String description;
    private String cep;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private byte[] documentPhoto;
    private List<ActuationArea> actuationAreas;
}
