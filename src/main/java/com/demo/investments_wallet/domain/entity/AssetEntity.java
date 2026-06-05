package com.demo.investments_wallet.domain.entity;

import com.demo.investments_wallet.domain.types.AssetCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetEntity {

    private String code;
    private String name;
    private AssetCategory category;
    private BigDecimal price;
    private String riskProfile;
    private String description;
}
