package com.spring.basics.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UpdateActuationAreaRequest {

    @Size(min = 1, message = "The field name must not be blank.")
    private String name;
    @Size(min = 1, message = "The field description must not be blank.")
    private String description;
    private Boolean isActive;
}
