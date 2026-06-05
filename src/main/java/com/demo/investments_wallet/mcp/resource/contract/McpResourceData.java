package com.demo.investments_wallet.mcp.resource.contract;

import lombok.Builder;

@Builder
public record McpResourceData(
        String uri,
        String name,
        String description,
        String mimeType,
        String content
) {

    public McpResourceData {
        validateRequired("uri", uri);
        validateRequired("name", name);
        validateRequired("description", description);
        validateRequired("mimeType", mimeType);
        validateRequired("content", content);
    }

    private static void validateRequired(String field, String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
    }
}

