package com.demo.investments_wallet.mcp.prompt.contract;

import io.modelcontextprotocol.server.McpServerFeatures;

public interface McpPromptDefinition {

    McpPromptData getData();


    default McpServerFeatures.SyncPromptSpecification build(){
        return McpSpecificationPromptBuilder.build(this.getData());
    }
}
