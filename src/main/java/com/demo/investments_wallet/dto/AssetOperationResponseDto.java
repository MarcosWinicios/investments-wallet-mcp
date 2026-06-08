package com.demo.investments_wallet.dto;

import com.demo.investments_wallet.domain.types.AssetCategory;
import com.demo.investments_wallet.domain.types.OperationType;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Builder
public record AssetOperationResponseDto(
        OperationType operationType,
        AssetCategory assetCategory,
        String assetCode,
        String assetName,
        BigDecimal transactionQuantity,
        BigDecimal transactionAmount,
        BigDecimal newQuantity,
        BigDecimal newAveragePrice,
        BigDecimal newAssetBalance
) {

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("operationType", operationType);
        map.put("assetCategory", assetCategory);
        map.put("assetCode", assetCode);
        map.put("assetName", assetName);
        map.put("transactionQuantity", transactionQuantity);
        map.put("transactionAmount", transactionAmount);
        map.put("newQuantity", newQuantity);
        map.put("newAveragePrice", newAveragePrice);
        map.put("newAssetBalance", newAssetBalance);
        return map;
    }
}
