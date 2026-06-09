package com.demo.investments_wallet.mcp.resource.contract;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import java.util.List;

public final class ResourceSpecificationBuilder {

    public static McpServerFeatures.SyncResourceSpecification build(McpResourceData data) {
        McpSchema.Resource resource = McpSchema.Resource.builder()
                .uri(data.uri())
                .name(data.name())
                .description(data.description())
                .mimeType(data.mimeType())
                .build();


        return new McpServerFeatures.SyncResourceSpecification(
                resource,
                (exchange, request) -> {
                    McpSchema.TextResourceContents contents = new McpSchema.TextResourceContents(
                            request.uri(),
                            data.mimeType(),
                            data.content()
                    );

                    return new McpSchema.ReadResourceResult(
                            List.of(contents)
                    );
                }
        );
    }
}

