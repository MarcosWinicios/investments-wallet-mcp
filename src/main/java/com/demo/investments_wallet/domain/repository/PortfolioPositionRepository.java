package com.demo.investments_wallet.domain.repository;

import com.demo.investments_wallet.domain.entity.PortfolioPositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PortfolioPositionRepository extends JpaRepository<PortfolioPositionEntity, Long> {

    Optional<PortfolioPositionEntity> findByAssetCode(String assetCode);

    @Query("""
            SELECT p
            FROM PortfolioPositionEntity p
            WHERE p.quantity > 0
            ORDER BY p.assetCode
            """)
    List<PortfolioPositionEntity> findAllActivePositions();
}
