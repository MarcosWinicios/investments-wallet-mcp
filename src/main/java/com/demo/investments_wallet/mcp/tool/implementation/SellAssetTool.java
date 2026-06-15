package com.demo.investments_wallet.mcp.tool.implementation;

import com.demo.investments_wallet.domain.service.PortfolioOperationService;
import com.demo.investments_wallet.dto.AssetOperationResponseDto;
import com.demo.investments_wallet.dto.SellAssetRequestDto;
import com.demo.investments_wallet.mcp.tool.contract.McpToolData;
import com.demo.investments_wallet.mcp.tool.contract.McpToolDefinition;
import com.demo.investments_wallet.mcp.tool.contract.McpToolExecutor;
import com.demo.investments_wallet.mcp.tool.contract.McpToolInputSchema;
import com.demo.investments_wallet.mcp.tool.contract.McpToolResponse;
import org.apache.coyote.BadRequestException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class SellAssetTool implements McpToolDefinition {

    private final PortfolioOperationService portfolioOperationService;

    public SellAssetTool(PortfolioOperationService portfolioOperationService) {
        this.portfolioOperationService = portfolioOperationService;
    }

    @Override
    public McpToolData getData() {
        return McpToolData.builder()
                .name("sell_asset")
                .title("Sell Asset")
                .description("Sell Asset")
                .inputSchema(this.buildInputSchema())
                .build();

    }

    @Override
    public McpToolResponse execute(Map<String, Object> parameters) {
        Map<String, Object> responseMap = new LinkedHashMap<>();

        try {
            if (Strings.isBlank(parameters.get("ticker").toString())) {
                throw new BadRequestException("Ticker is required");
            }

            if (Strings.isBlank(parameters.get("quantity").toString())) {
                throw new BadRequestException("Quantity is required");
            }

            String ticker = parameters.get("ticker").toString();
            String quantity = parameters.get("quantity").toString();
            BigDecimal quantityDecimal = new BigDecimal(quantity);

            SellAssetRequestDto requestDto =  new SellAssetRequestDto(ticker, quantityDecimal);

            AssetOperationResponseDto response = this.portfolioOperationService.sellAsset(requestDto);
            responseMap = response.toMap();
            return McpToolResponse.success(responseMap);
        } catch (Exception ex) {
            this.logError(ex);
            responseMap.put("error", ex.getMessage());
            return McpToolResponse.failure(responseMap);
        }
    }


    private McpToolInputSchema  buildInputSchema() {
        McpToolInputSchema inputSchema = McpToolInputSchema.builder()
                .isRequired(true)
                .type("object")
                .build();

        inputSchema.addProperty("ticker", "Ticker symbol of the asset to sell.", "string", true);
        inputSchema.addProperty("quantity", "Quantity of the asset to sell.", "number", true);

        return inputSchema;
    }
}
