package com.demo.investments_wallet.mcp.resource.contract;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import java.util.List;

public final class ResourceSpecificationBuilder {

    private ResourceSpecificationBuilder() {
    }

    public static McpServerFeatures.SyncResourceSpecification build(
            String uri,
            String name,
            String description,
            String mimetype,
            String content
    ) {
        McpResourceData resourceData = new McpResourceData(
                uri,
                name,
                description,
                mimetype,
                content
        );

        return build(resourceData);
    }

    public static McpServerFeatures.SyncResourceSpecification build(McpResourceData data) {
        McpSchema.Resource resource = McpSchema.Resource.builder()
                .uri(data.uri())
                .name(data.name())
                .description(data.description())
                .mimeType(data.mimeType())
                .build();

        McpSchema.TextResourceContents contents = new McpSchema.TextResourceContents(
                data.uri(),
                data.mimeType(),
                data.content()
        );

        McpSchema.ReadResourceResult result = new McpSchema.ReadResourceResult(
                List.of(contents)
        );

        McpServerFeatures.SyncResourceSpecification specification = new McpServerFeatures.SyncResourceSpecification(
                resource,
                (exchange, request) -> result
        );

        return specification;
    }
}

