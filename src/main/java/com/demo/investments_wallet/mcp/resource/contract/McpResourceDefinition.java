package com.demo.investments_wallet.mcp.resource.contract;

import io.modelcontextprotocol.server.McpServerFeatures;

public interface McpResourceDefinition {

    McpResourceData data();

    default String getName() {
        return this.data().name();
    }

    default McpServerFeatures.SyncResourceSpecification build() {
        return ResourceSpecificationBuilder.build(
                this.data()
        );
    }
}

