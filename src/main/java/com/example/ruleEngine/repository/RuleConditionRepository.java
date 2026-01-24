package com.example.ruleEngine.repository;

import com.example.ruleEngine.entity.RuleCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleConditionRepository extends JpaRepository<RuleCondition, Long> {
}
