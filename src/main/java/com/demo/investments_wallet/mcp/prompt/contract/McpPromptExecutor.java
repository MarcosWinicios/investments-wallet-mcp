package com.demo.investments_wallet.mcp.prompt.contract;

import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class McpPromptExecutor {

    public static McpSchema.GetPromptResult execute(
            McpSyncServerExchange exchange,
            McpSchema.GetPromptRequest request,
            McpPromptData promptData
    ) {

        String text = promptData.text();

        text = text.formatted(request.arguments());

        McpSchema.PromptMessage message =
                new McpSchema.PromptMessage(
                        McpSchema.Role.USER,
                        new McpSchema.TextContent(text)
                );

        return new McpSchema.GetPromptResult(
                (promptData.resultMessage() == null ? null : promptData.resultMessage()),
                List.of(message)
        );
    }

}
