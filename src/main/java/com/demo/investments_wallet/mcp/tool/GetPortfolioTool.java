package com.demo.investments_wallet.mcp.tool;

import com.demo.investments_wallet.domain.service.PortfolioQueryService;
import com.demo.investments_wallet.dto.PortfolioSummaryResponseDto;
import org.springframework.stereotype.Component;

@Component
public class GetPortfolioTool {

    private final PortfolioQueryService portfolioQueryService;

    public GetPortfolioTool(PortfolioQueryService portfolioQueryService) {
        this.portfolioQueryService = portfolioQueryService;
    }

    public PortfolioSummaryResponseDto execute() {
        return portfolioQueryService.getPortfolio();
    }
}
