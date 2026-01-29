package com.example.ruleEngine.repository;

import com.example.ruleEngine.entity.RuleAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleActionRepository extends JpaRepository<RuleAction, Long> {
    List<RuleAction> findByRuleId(Long id);
}
