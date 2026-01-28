package com.example.ruleEngine.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActionRequestDto {

    @NotBlank(message = "Field should not be blank")
    private String outputField;

    @NotBlank(message = "Field should not be blank")
    private String outputValue;
}
