package com.demo.investments_wallet.mcp.tool.contract;

import lombok.Builder;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Getter
public class McpToolInputSchema {

    private final String type;
    private final Boolean isRequired;
    private final Map<String, McpSchemaProperty> properties = new LinkedHashMap<>();

    public void addProperty(String name, String description, String type, Boolean isRequired){
        this.properties.put(
                name,
                McpSchemaProperty.of(name, description, type, isRequired)
        );
    }

    @Builder
    public McpToolInputSchema(String type, Boolean isRequired) {
        validateRequired("type", type);
        validateRequired("isRequired", Objects.toString(isRequired));

        this.type = type;
        this.isRequired = isRequired;
    }

    private static void validateRequired(String field, String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
    }
}