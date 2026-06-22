package com.demo.investments_wallet.dto;

import com.demo.investments_wallet.domain.types.OperationType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TransactionHistoryFilterRequestDto(
        String assetCode,
        OperationType operationType,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
