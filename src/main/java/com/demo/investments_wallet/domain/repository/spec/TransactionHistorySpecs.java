package com.demo.investments_wallet.domain.repository.spec;

import com.demo.investments_wallet.domain.entity.TransactionHistoryEntity;
import com.demo.investments_wallet.domain.types.OperationType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TransactionHistorySpecs {

    public static Specification<TransactionHistoryEntity> withAssetCode(String assetCode) {
        return (root, query, builder) -> {
            if (assetCode == null) {
                return builder.conjunction();
            }

            return builder.equal(root.get("assetCode"), assetCode);
        };
    }

    public static Specification<TransactionHistoryEntity> withOperationType(OperationType operationType) {
        return (root, query, builder) -> {
            if (operationType == null) {
                return builder.conjunction();
            }

            return builder.equal(root.get("operationType"), operationType);
        };
    }

    public static Specification<TransactionHistoryEntity> withStartDate(LocalDateTime startDate) {
        return (root, query, builder) -> {
            if (startDate == null) {
                return builder.conjunction();
            }

            return builder.greaterThanOrEqualTo(
                    root.get("operationTimestamp"),
                    startDate
            );
        };
    }

    public static Specification<TransactionHistoryEntity> withEndDate(LocalDateTime endDate) {
        return (root, query, builder) -> {
            if (endDate == null) {
                return builder.conjunction();
            }

            return builder.lessThanOrEqualTo(
                    root.get("operationTimestamp"),
                    endDate
            );
        };
    }
}
