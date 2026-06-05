package com.demo.investments_wallet.mcp.tool.contract;

import java.time.Instant;
import java.util.Map;

public record McpToolResponse(
        Boolean success,
        Map<String, Object> data,
        Instant timestamp
) {

        public static McpToolResponse success(Map<String, Object> data) {
            return new McpToolResponse(true, data, Instant.now());
        }

        public static McpToolResponse failure(Map<String, Object> data) {
            return new McpToolResponse(false, data, Instant.now());
        }
}
