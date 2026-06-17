package com.demo.investments_wallet.mcp.prompt.contract;

import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

@Builder
public record McpPromptArgumentData(
        String name,
        String description,
        boolean isRequired
) {

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("description", description);
        map.put("isRequired", isRequired);
        return map;
    }
}
