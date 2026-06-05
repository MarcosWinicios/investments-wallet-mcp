package com.demo.investments_wallet.mcp.to;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ToolDataResumeTo {
    private final String name;
    private final String title;
    private final String description;
    private String inputSchema;
}
