package com.demo.investments_wallet.config;

import com.demo.investments_wallet.mcp.resource.contract.McpResourceDefinition;
import io.modelcontextprotocol.server.McpServerFeatures;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class McpResourcesConfig {

    @Bean
    List<McpServerFeatures.SyncResourceSpecification> resources(List<McpResourceDefinition> resources){

        List<McpServerFeatures.SyncResourceSpecification> result = resources.stream()
                .map(McpResourceDefinition::build)
                .toList();

        return result;
    }
}
