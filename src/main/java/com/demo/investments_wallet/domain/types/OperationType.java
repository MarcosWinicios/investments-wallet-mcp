package com.demo.investments_wallet.domain.types;

public enum OperationType {
    BUY,
    SELL;

    public static OperationType fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("OperationType value cannot be null or empty");
        }

        try {
            return OperationType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Invalid OperationType: '" + value + "'. Valid values are: BUY, SELL");
        }
    }
}
