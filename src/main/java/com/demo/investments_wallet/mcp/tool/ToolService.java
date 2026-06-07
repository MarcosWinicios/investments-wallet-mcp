package com.demo.investments_wallet.mcp.tool;

import com.demo.investments_wallet.domain.service.PortfolioOperationService;
import com.demo.investments_wallet.mcp.tool.contract.McpToolResponse;
import com.demo.investments_wallet.mcp.tool.implementation.BuyAssetTool;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ToolService {

    private final PortfolioOperationService portfolioOperationService;

    public ToolService(PortfolioOperationService portfolioOperationService) {
        this.portfolioOperationService = portfolioOperationService;
    }

    @Transactional
    public McpToolResponse save(Map<String, Object> params) {
        BuyAssetTool tool =  new BuyAssetTool(portfolioOperationService);
        McpToolResponse result = tool.execute(params);
        return  result;
    }
}
