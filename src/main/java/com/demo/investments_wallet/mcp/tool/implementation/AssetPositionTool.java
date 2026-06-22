package com.demo.investments_wallet.mcp.tool.implementation;

import com.demo.investments_wallet.domain.service.PortfolioQueryService;
import com.demo.investments_wallet.dto.PortfolioPositionViewDto;
import com.demo.investments_wallet.mcp.tool.contract.McpToolData;
import com.demo.investments_wallet.mcp.tool.contract.McpToolDefinition;
import com.demo.investments_wallet.mcp.tool.contract.McpToolInputSchema;
import com.demo.investments_wallet.mcp.tool.contract.McpToolResponse;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class AssetPositionTool implements McpToolDefinition {

    private final PortfolioQueryService portfolioQueryService;

    public AssetPositionTool(PortfolioQueryService portfolioQueryService) {
        this.portfolioQueryService = portfolioQueryService;
    }


    @Override
    public McpToolData getData() {

        return McpToolData.builder()
                .name("asset_position")
                .description("Find asset position. Total amount, average price and more data")
                .title("Asset position")
                .inputSchema(this.buildInputSchema())
                .build();
    }

    private McpToolInputSchema buildInputSchema() {
        McpToolInputSchema result = McpToolInputSchema.builder()
                .type("object")
                .isRequired(true)
                .build();

        result.addProperty("ticker", "Ticker symbol of the asset to buy.", "string", true);

        return  result;
    }

    @Override
    public McpToolResponse execute(Map<String, Object> parameters) {
        Map<String, Object> response = new LinkedHashMap<>();

        try {
            String ticker = (String) parameters.get("ticker");

            if(ticker.isBlank()) {
                throw new IllegalArgumentException("ticker is null or empty");
            }

            PortfolioPositionViewDto positionDto = this.portfolioQueryService.getPortfolioAssetPosition(ticker);
            response = positionDto.getMap();

            return McpToolResponse.success(response);
        } catch (Exception ex) {
            this.logError(ex);
            response.put("error", ex.getMessage());
            return McpToolResponse.failure(response);
        }


    }
}
