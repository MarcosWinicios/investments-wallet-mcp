package com.demo.investments_wallet.dto;

import com.demo.investments_wallet.domain.types.OperationType;
import java.time.LocalDateTime;

public record TransactionHistoryFilterRequestDto(
        String assetCode,
        OperationType operationType,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
