package com.demo.investments_wallet.dto;

import com.demo.investments_wallet.domain.types.AssetCategory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record PortfolioSummaryResponseDto(
        List<PortfolioPositionViewDto> positions,
        BigDecimal totalPortfolioValue,
        Map<AssetCategory, BigDecimal> categoryAllocation
) {

    public Map<String, Object> getMap() {
        final Map<String, Object> result = new HashMap<>();
        final List<Map<String, Object>> portfolioPositions = new ArrayList<>();

        positions.forEach(position -> {
            portfolioPositions.add(position.getMap());
        });

        result.put("positions", portfolioPositions);
        result.put("total",  totalPortfolioValue);
        result.put("categoryAllocation", categoryAllocation);

        return result;
    }
}
