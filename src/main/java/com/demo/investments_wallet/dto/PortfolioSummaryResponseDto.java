package com.demo.investments_wallet.dto;

import com.demo.investments_wallet.domain.types.AssetCategory;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record PortfolioSummaryResponseDto(
        List<PortfolioPositionViewDto> positions,
        BigDecimal totalPortfolioValue,
        Map<AssetCategory, BigDecimal> categoryAllocation
) {
}
