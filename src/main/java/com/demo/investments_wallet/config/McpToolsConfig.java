package com.demo.investments_wallet.config;

import com.demo.investments_wallet.mcp.tool.contract.McpToolDefinition;

import java.util.List;

import io.modelcontextprotocol.server.McpServerFeatures;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpToolsConfig {

    @Bean
    List<McpServerFeatures.SyncToolSpecification> tools(List<McpToolDefinition> tools){
        return tools.stream()
                .map(McpToolDefinition::build)
                .toList();
    }

}
