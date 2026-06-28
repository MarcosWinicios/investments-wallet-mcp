package com.demo.investments_wallet.domain.repository;

import com.demo.investments_wallet.domain.entity.TransactionHistoryEntity;
import com.demo.investments_wallet.domain.types.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistoryEntity, Long>, JpaSpecificationExecutor<TransactionHistoryEntity> {

    @Query("""
            SELECT t
            FROM TransactionHistoryEntity t
            WHERE (:assetCode IS NULL OR t.assetCode = :assetCode)
              AND (:operationType IS NULL OR t.operationType = :operationType)
              AND (:startDate IS NULL OR t.operationTimestamp >= :startDate)
              AND (:endDate IS NULL OR t.operationTimestamp <= :endDate)
            ORDER BY t.operationTimestamp ASC
            """)
    List<TransactionHistoryEntity> findByFilters(
            @Param("assetCode") String assetCode,
            @Param("operationType") OperationType operationType,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
