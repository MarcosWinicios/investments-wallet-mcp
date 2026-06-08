package com.demo.investments_wallet.mcp.tool.contract;

import com.demo.investments_wallet.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ToolSpecificationBuilder {

    private static JsonUtil jsonUtil;

    private ToolSpecificationBuilder(JsonUtil jsonUtil) {
        ToolSpecificationBuilder.jsonUtil = jsonUtil;
    }

    public static McpServerFeatures.SyncToolSpecification build(McpToolDefinition definition) {

        Map<String, Object> inputSchema = resolveInputSchema(definition.getData().inputSchema());

        McpSchema.Tool tool = McpSchema.Tool.builder()
                .name(definition.getData().name())
                .description(definition.getData().description())
                .title(definition.getData().title())
                .inputSchema(inputSchema)
                .build();

        McpServerFeatures.SyncToolSpecification createdTool = McpServerFeatures.SyncToolSpecification.builder()
                .tool(tool)
                .callHandler((exchange, request) -> {
                    log.info("Tool Executor[{}] - execution start...", tool.name());
                    Map<String, Object> arguments = request.arguments();

                    log.debug("Tool Executor[{}] - received arguments {}", tool.name(), arguments.toString());

                    McpToolResponse result = definition.execute(arguments);
                    McpSchema.CallToolResult callToolResult = toCallToolResult(result, tool.name());

                    log.debug("Tool Executor[{}] - response tool execution: {}",  tool.name(), callToolResult.toString());

                    log.info("Tool Executor[{}] - execution end...", tool.name());
                    return callToolResult;
                })
                .build();
        log.info("registered tool: [{}] - inputSchema: {}", tool.name(), inputSchema.toString());
        return createdTool;
    }


    private static McpSchema.CallToolResult toCallToolResult(McpToolResponse mcpToolResponse, String toolName) {
        log.info("Tool Executor[{}] -  building response...",  toolName);
        try {

            String json = jsonUtil.toJson(mcpToolResponse);

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

    private static Map<String, Object> resolveInputSchema(McpToolInputSchema inputSchema) {
        final Map<String, Object> result = new LinkedHashMap<>();
        final Map<String, Object> properties = new LinkedHashMap<>();

        inputSchema.getProperties().forEach((name, property) -> {
            final Map<String, Object> propertyMap = new LinkedHashMap<>();

            propertyMap.put("description", property.description());
            propertyMap.put("type", property.type());

            properties.put(name, propertyMap);
        });


        List<String> requiredFields = inputSchema.getProperties().entrySet().stream()
                .filter(entry -> entry.getValue().isRequired())
                .map(Map.Entry::getKey)
                .toList();

        result.put("type", inputSchema.getType());
        result.put("required", requiredFields);
        result.put("properties", properties);
        return result;
    }
}
