package com.ruipeng.risk_assessment.repo;

import com.ruipeng.risk_assessment.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
}
