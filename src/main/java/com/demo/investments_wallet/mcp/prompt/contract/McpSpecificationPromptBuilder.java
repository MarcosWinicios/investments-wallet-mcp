package com.demo.investments_wallet.mcp.prompt.contract;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class McpSpecificationPromptBuilder extends McpPromptExecutor {

    @Bean
    public static McpServerFeatures.SyncPromptSpecification build(McpPromptData mcpPromptData) {

        return new McpServerFeatures.SyncPromptSpecification(
                resolvePromptDefinition(mcpPromptData),
                (mcpSyncServerExchange, getPromptRequest) -> {
                    return execute(mcpSyncServerExchange, getPromptRequest, mcpPromptData);
                }
        );
    }


    private static McpSchema.Prompt resolvePromptDefinition(McpPromptData mcpPromptData) {
        return new McpSchema.Prompt(
                mcpPromptData.getName(),
                mcpPromptData.getDescription(),
                resolveArguments(mcpPromptData)
        );
    }

    private static List<McpSchema.PromptArgument> resolveArguments(McpPromptData mcpPromptData) {
        return mcpPromptData.getArguments().stream()
                .map(McpSpecificationPromptBuilder::resolveArgument)
                .toList();
    }

    private static McpSchema.PromptArgument resolveArgument(McpPromptArgumentData argumentData) {
        return new McpSchema.PromptArgument(
                argumentData.name(),
                argumentData.description(),
                argumentData.isRequired()
        );
    }
}
