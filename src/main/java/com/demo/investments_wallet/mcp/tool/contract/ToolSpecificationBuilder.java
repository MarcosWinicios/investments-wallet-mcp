package com.demo.investments_wallet.mcp.tool.contract;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ToolSpecificationBuilder extends McpToolExecutor {

    public static McpServerFeatures.SyncToolSpecification build(McpToolDefinition definition) {
        try {
            Map<String, Object> inputSchema = resolveInputSchema(definition.getData().inputSchema());

            McpSchema.Tool tool = McpSchema.Tool.builder()
                    .name(definition.getData().name())
                    .description(definition.getData().description())
                    .title(definition.getData().title())
                    .inputSchema(inputSchema)
                    .build();

            McpServerFeatures.SyncToolSpecification createdTool = McpServerFeatures.SyncToolSpecification.builder()
                    .tool(tool)
                    .callHandler((exchange, request) -> McpToolExecutor.execute(
                            exchange, request, tool, definition
                    ))
                    .build();
            log.info("registered tool: [{}] - {}", tool.name(), inputSchema);
            return createdTool;
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
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
