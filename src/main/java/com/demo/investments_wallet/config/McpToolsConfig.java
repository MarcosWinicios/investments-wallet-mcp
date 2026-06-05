package com.demo.investments_wallet.config;

import com.demo.investments_wallet.mcp.tool.contract.McpToolDefinition;
import com.demo.investments_wallet.mcp.tool.implementation.BuyAssetTool;
import com.demo.investments_wallet.mcp.tool.GetPortfolioTool;
import com.demo.investments_wallet.mcp.tool.GetTransactionHistoryTool;
import com.demo.investments_wallet.mcp.tool.SellAssetTool;
import java.util.List;

import io.modelcontextprotocol.server.McpServerFeatures;
import org.springframework.ai.mcp.annotation.McpTool;
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
