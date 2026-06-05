package com.demo.investments_wallet.dto;

import java.math.BigDecimal;

public record SellAssetRequestDto(
        String assetCode,
        BigDecimal quantity
) {
}
