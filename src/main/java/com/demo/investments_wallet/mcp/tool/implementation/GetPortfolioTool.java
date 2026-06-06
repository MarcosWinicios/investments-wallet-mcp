package com.demo.investments_wallet.mcp.tool.implementation;

import com.demo.investments_wallet.domain.service.PortfolioQueryService;
import com.demo.investments_wallet.dto.PortfolioSummaryResponseDto;
import com.demo.investments_wallet.mcp.tool.contract.McpToolData;
import com.demo.investments_wallet.mcp.tool.contract.McpToolDefinition;
import com.demo.investments_wallet.mcp.tool.contract.McpToolInputSchema;
import com.demo.investments_wallet.mcp.tool.contract.McpToolResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GetPortfolioTool  implements McpToolDefinition {

    private final PortfolioQueryService portfolioQueryService;

    public GetPortfolioTool(PortfolioQueryService portfolioQueryService) {
        this.portfolioQueryService = portfolioQueryService;
    }


    @Override
    public McpToolData getData() {
        return McpToolData.builder()
                .name("get_portfolio")
                .title("List Portfolio")
                .description("Tool to list personal portfolio")
                .inputSchema(this.buildInputSchema())
                .build();
    }

    private McpToolInputSchema buildInputSchema() {
        return McpToolInputSchema.builder()
                .isRequired(false)
                .type("string")
                .build();
    }

    @Override
    public McpToolResponse execute(Map<String, Object> parameters) {
        PortfolioSummaryResponseDto portfolio = this.portfolioQueryService.getPortfolio();

        return McpToolResponse.success(portfolio.getMap());
    }
}
