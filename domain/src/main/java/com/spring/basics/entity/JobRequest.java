package com.spring.basics.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class JobRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idCustomer;
    private Long interestedWorkers;
    private String cep;
    private String city;
    private String latitude;
    private String longitude;
    private String formattedAddress;
    private String jobInformation;
    private Long idActuationArea;
    @Enumerated(EnumType.STRING)
    private PriorityEnum priority;
    private LocalDate startDate;
    @Enumerated(EnumType.STRING)
    private JobRequestStatusEnum status;
    private LocalDateTime createdAt;
    private Long retryAmount;


}
