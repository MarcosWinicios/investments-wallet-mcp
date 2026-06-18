package com.demo.investments_wallet.mcp.tool.contract;

import com.demo.investments_wallet.utils.JsonUtil;
import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public abstract class McpToolExecutor {

    public static McpSchema.CallToolResult  execute(
            McpSyncServerExchange exchange,
            McpSchema.CallToolRequest request,
            McpSchema.Tool tool,
            McpToolDefinition toolDefinition
    ) {


        log.info("[{}] ToolExecutor[{}] - execution start...", exchange.sessionId(), tool.name());
        Map<String, Object> arguments = request.arguments();

        log.debug("[{}] ToolExecutor[{}] - received arguments {}", exchange.sessionId(), tool.name(), arguments.toString());

        McpToolResponse businessResult = toolDefinition.execute(arguments);
        McpSchema.CallToolResult mcpResult = toCallToolResult(businessResult, tool.name(), exchange.sessionId());

        if(log.isDebugEnabled()) {
            String resulStr = mcpResult.content().stream()
                    .filter(x -> x instanceof McpSchema.TextContent)
                    .findFirst()
                    .map(McpSchema.TextContent.class::cast)
                    .map(McpSchema.TextContent::text)
                    .orElseGet(String::new);

            log.debug("[{}] ToolExecutor[{}] - response tool execution: {}", exchange.sessionId(), tool.name(), resulStr);
        }

        log.info("[{}] ToolExecutor[{}] - execution end...",  exchange.sessionId(), tool.name());

        return mcpResult;
    }
    private static McpSchema.CallToolResult toCallToolResult(McpToolResponse mcpToolResponse, String toolName, String sessionId) {
        log.info("[{}] ToolExecutor[{}] -  building response...",  sessionId, toolName);
        try {

            String json = JsonUtil.toJson(mcpToolResponse);

            List<McpSchema.Content> content = List.of(
                    new McpSchema.TextContent(json)
            );

            return McpSchema.CallToolResult.builder()
                    .isError(!mcpToolResponse.success())
                    .content(content)
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
