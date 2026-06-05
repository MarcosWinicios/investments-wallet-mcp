package com.demo.investments_wallet.dto;

import com.demo.investments_wallet.domain.types.AssetCategory;
import com.demo.investments_wallet.domain.types.OperationType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionHistoryEntryDto(
        OperationType operationType,
        String assetCode,
        String assetName,
        AssetCategory assetCategory,
        BigDecimal quantity,
        BigDecimal totalAmount,
        LocalDateTime operationTimestamp
) {
}
