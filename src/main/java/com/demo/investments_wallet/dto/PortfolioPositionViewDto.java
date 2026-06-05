package com.demo.investments_wallet.dto;

import com.demo.investments_wallet.domain.types.AssetCategory;
import java.math.BigDecimal;

public record PortfolioPositionViewDto(
        String assetCode,
        String assetName,
        AssetCategory assetCategory,
        BigDecimal quantity,
        BigDecimal averagePrice,
        BigDecimal referencePrice,
        BigDecimal positionValue
) {
}
