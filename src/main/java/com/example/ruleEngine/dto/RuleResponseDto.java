package com.example.ruleEngine.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RuleResponseDto {

    private Long id;
    private String name;
    private boolean active;
    private String priority;
    private LocalDateTime createdAt;
}
