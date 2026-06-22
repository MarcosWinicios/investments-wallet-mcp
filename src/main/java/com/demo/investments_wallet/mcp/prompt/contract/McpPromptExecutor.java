package com.demo.investments_wallet.mcp.prompt.contract;

import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public abstract class McpPromptExecutor {

    public static McpSchema.GetPromptResult execute(
            McpSyncServerExchange exchange,
            McpSchema.GetPromptRequest request,
            McpPromptData promptData
    ) {
        log.debug("McpPromptExecutor execute start...");

        String text = promptData.getText();

        text = text.formatted(request.arguments());

        McpSchema.PromptMessage message =
                new McpSchema.PromptMessage(
                        McpSchema.Role.USER,
                        new McpSchema.TextContent(text)
                );

        McpSchema.GetPromptResult result = new McpSchema.GetPromptResult(
                promptData.getResultMessage(),
                List.of(message)
        );

        log.debug("McpPromptExecutor execute end...");

        return result;
    }

}
