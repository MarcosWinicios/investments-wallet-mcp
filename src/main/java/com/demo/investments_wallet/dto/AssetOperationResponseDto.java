package com.demo.investments_wallet.dto;

import com.demo.investments_wallet.domain.types.AssetCategory;
import com.demo.investments_wallet.domain.types.OperationType;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public record AssetOperationResponseDto(
        OperationType operationType,
        String assetCode,
        String assetName,
        AssetCategory assetCategory,
        BigDecimal quantity,
        BigDecimal totalAmount,
        BigDecimal updatedQuantity
) {

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("operationType", operationType);
        map.put("assetCode", assetCode);
        map.put("assetName", assetName);
        map.put("assetCategory", assetCategory);
        map.put("quantity", quantity);
        map.put("totalAmount", totalAmount);
        map.put("updatedQuantity", updatedQuantity);
        return map;
    }
}
