package com.demo.investments_wallet.mcp.tool.contract;

import io.modelcontextprotocol.server.McpServerFeatures;
import org.apache.coyote.BadRequestException;

import java.util.Map;

public interface McpToolDefinition {

    McpToolData getData();

    McpToolResponse execute(Map<String, Object> parameters);

    default McpServerFeatures.SyncToolSpecification build(){
        return ToolSpecificationBuilder.build(
                this
        );
    }
}
