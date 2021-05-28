package com.spring.basics.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class CreateAvaliationRequest {

    @Size(min = 1, message = "The field name must not be blank.")
    private String description;
    @NotNull(message = "The field name is mandatory.")
    @Min(value = 1, message = "idWorker is invalid.")
    private Long idWorker;
    @NotNull(message = "The field name is mandatory.")
    @Min(value = 1, message = "idCustomer is invalid.")
    private Long idCustomer;
    @NotNull(message = "The field rating is mandatory.")
    @Min(value = 1, message = "The rating must be between 0 and 5.")
    @Max(value = 5, message = "The rating must be between 0 and 5.")
    private Long rating;
}
