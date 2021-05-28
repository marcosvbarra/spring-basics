package com.spring.basics.api.response;

import com.spring.basics.entity.ActuationArea;
import com.spring.basics.entity.Avaliation;
import com.spring.basics.entity.PlanEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WorkerResponse {

    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String cpf;
    private PlanEnum plan;
    private Long remainingContacts;
    private Long revealedContacts;
    private LocalDateTime renewalDate;
    private String cep;
    private String city;
    private String latitude;
    private String longitude;
    private String formattedAddress;
    private String description;
    private Double rating;
    private Boolean isActive;
    private byte[] documentPhoto;
    private LocalDateTime createdAt;
    private List<Avaliation> avaliations;
    private List<ActuationArea> actuationAreas;
}
