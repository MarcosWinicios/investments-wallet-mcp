package com.demo.investments_wallet.mcp.to;

import com.demo.investments_wallet.mcp.resource.contract.McpResourceData;

public record ResourceDataResumeTo(
        String uri,
        String name,
        String description,
        String mimeType
) {
    public ResourceDataResumeTo(McpResourceData data) {
        this(
                data.uri(),
                data.name(),
                data.description(),
                data.mimeType()
        );
    }
}
