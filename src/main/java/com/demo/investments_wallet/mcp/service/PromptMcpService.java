package com.demo.investments_wallet.mcp.service;

import com.demo.investments_wallet.mcp.prompt.contract.McpPromptData;
import com.demo.investments_wallet.mcp.prompt.contract.McpPromptDefinition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromptMcpService {


    private final List<McpPromptDefinition> availablePrompts;

    public PromptMcpService(List<McpPromptDefinition> availablePrompts) {
        this.availablePrompts = availablePrompts;
    }


    public List<McpPromptData> getAvailablePrompts() {
        return availablePrompts.stream()
                .map(McpPromptDefinition::getData)
                .toList();
    }
}
