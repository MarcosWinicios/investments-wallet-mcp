package com.demo.investments_wallet.domain.types;

public enum OperationType {
    BUY,
    SELL;

    public static OperationType fromString(String value) {
        if (value == null || value.trim().isEmpty() || value.isBlank()) {
            return null;
        }

        try {
            return OperationType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Invalid OperationType: '" + value + "'. Valid values are: BUY, SELL");
        }
    }
}
