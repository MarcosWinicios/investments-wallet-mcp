package com.demo.investments_wallet.config;

import com.demo.investments_wallet.mcp.prompt.contract.McpPromptDefinition;
import com.demo.investments_wallet.mcp.resource.contract.McpResourceDefinition;
import com.demo.investments_wallet.mcp.tool.contract.McpToolDefinition;
import io.modelcontextprotocol.server.McpServerFeatures;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class McpConfig {

    @Bean
    List<McpServerFeatures.SyncResourceSpecification> resources(List<McpResourceDefinition> resources){
        return resources.stream()
                .map(McpResourceDefinition::build)
                .toList();
    }

    @Bean
    List<McpServerFeatures.SyncToolSpecification> tools(List<McpToolDefinition> tools){
        return tools.stream()
                .map(McpToolDefinition::build)
                .toList();
    }

    @Bean
    List<McpServerFeatures.SyncPromptSpecification> prompts(List<McpPromptDefinition> prompts){
        return prompts.stream()
                .map(McpPromptDefinition::build)
                .toList();
    }
}
