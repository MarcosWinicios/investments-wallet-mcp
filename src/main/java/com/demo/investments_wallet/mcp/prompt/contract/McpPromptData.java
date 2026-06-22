package com.demo.investments_wallet.mcp.prompt.contract;


import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class McpPromptData {
    private final String name;
    private final String title;
    private final String description;
    private final List<McpPromptArgumentData> arguments = new ArrayList<>();
    private final String text;
    private final String resultMessage;

    public McpPromptData(String name, String title, String description, String text, String resultMessage) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.text = text;
        this.resultMessage = resultMessage;
    }

    public void addArgument(String name, String title, String description, boolean required) {
        arguments.add(McpPromptArgumentData.builder()
                .name(name)
                .title(title)
                .description(description)
                .isRequired(required)
                .build());
    }

    private List<Map<String, Object>> argumentsToMapList() {
        return this.arguments.stream()
                .map(McpPromptArgumentData::toMap)
                .toList();
    }
}
