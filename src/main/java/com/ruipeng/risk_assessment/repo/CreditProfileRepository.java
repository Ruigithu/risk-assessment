package com.ruipeng.risk_assessment.repo;

import com.ruipeng.risk_assessment.entity.CreditProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditProfileRepository extends JpaRepository<CreditProfile, Long> {
}
