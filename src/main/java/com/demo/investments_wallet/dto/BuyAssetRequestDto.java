package com.demo.investments_wallet.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BuyAssetRequestDto(
        String assetCode,
        BigDecimal quantity,
        BigDecimal totalAmount
) {
}
