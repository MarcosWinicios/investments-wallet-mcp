package com.demo.investments_wallet.mcp.prompt.contract;


import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record McpPromptData(
        String name,
        String description,
        List<McpPromptArgumentData> arguments,
        String text,
        String resultMessage

) {

    public void addArgument(String name, String description, boolean required) {
        arguments.add(McpPromptArgumentData.builder()
                        .name(name)
                        .description(description)
                        .isRequired(required)
                .build());
    }

    private List<Map<String, Object>> argumentsToMapList(){
        return this.arguments.stream()
                .map(McpPromptArgumentData::toMap)
                .toList();
    }
}
