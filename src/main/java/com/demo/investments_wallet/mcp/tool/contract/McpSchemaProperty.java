package com.demo.investments_wallet.mcp.tool.contract;

import lombok.Builder;

@Builder
public record McpSchemaProperty(
        String name,
        String description,
        String type,
        Boolean isRequired
) {

    static McpSchemaProperty of(String name, String description, String type, Boolean isRequired) {
        validateRequired("name", name);
        validateRequired("description", description);
        validateRequired("type", type);
        return new McpSchemaProperty(name, description, type, isRequired);
    }

    private static void validateRequired(String field, String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
    }
}
