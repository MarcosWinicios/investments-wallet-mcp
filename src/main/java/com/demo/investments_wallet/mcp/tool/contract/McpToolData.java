package com.demo.investments_wallet.mcp.tool.contract;

import lombok.Builder;

@Builder
public record McpToolData (
        String name,
        String title,
        String description,
        McpToolInputSchema inputSchema
){
    public McpToolData {
        validateRequired("name", name);
        validateRequired("title", title);
        validateRequired("description", description);
    }

    private static void validateRequired(String field, String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
    }
}
