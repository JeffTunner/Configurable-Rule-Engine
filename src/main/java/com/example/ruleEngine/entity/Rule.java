package com.example.ruleEngine.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rule {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private boolean active;
    private String priority;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "rule", cascade = CascadeType.ALL)
    private List<RuleCondition> conditions;

    @OneToOne(mappedBy = "rule", cascade = CascadeType.ALL)
    private RuleAction action;
}
