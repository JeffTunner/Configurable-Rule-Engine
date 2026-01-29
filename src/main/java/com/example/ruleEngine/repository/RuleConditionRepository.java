package com.example.ruleEngine.repository;

import com.example.ruleEngine.entity.RuleCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleConditionRepository extends JpaRepository<RuleCondition, Long> {
    List<RuleCondition> findByRuleId(Long id);
}
