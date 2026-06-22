package com.demo.investments_wallet.domain.types;

public enum AssetCategory {
    FII,
    STOCK,
    CDB;

    public static AssetCategory fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("AssetCategory value cannot be null or empty");
        }

        try {
            return AssetCategory.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Invalid AssetCategory: '" + value + "'. Valid values are: FII, STOCK, CDB");
        }
    }
}
