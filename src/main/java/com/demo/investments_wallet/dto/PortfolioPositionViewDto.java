package com.demo.investments_wallet.dto;

import com.demo.investments_wallet.domain.types.AssetCategory;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public record PortfolioPositionViewDto(
        String assetCode,
        String assetName,
        AssetCategory assetCategory,
        BigDecimal quantity,
        BigDecimal averagePrice,
        BigDecimal referencePrice,
        BigDecimal positionValue
) {

    public Map<String, Object>  getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("assetCode", assetCode);
        map.put("assetName", assetName);
        map.put("assetCategory", assetCategory.name());
        map.put("quantity", quantity);
        map.put("averagePrice", averagePrice);
        map.put("referencePrice", referencePrice);
        map.put("positionValue", positionValue);

        return map;
    }
}
