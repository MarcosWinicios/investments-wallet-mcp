package com.demo.investments_wallet.mcp.tool.implementation;

import com.demo.investments_wallet.domain.service.PortfolioOperationService;
import com.demo.investments_wallet.dto.AssetOperationResponseDto;
import com.demo.investments_wallet.dto.BuyAssetRequestDto;
import com.demo.investments_wallet.mcp.tool.contract.McpToolData;
import com.demo.investments_wallet.mcp.tool.contract.McpToolDefinition;

import com.demo.investments_wallet.mcp.tool.contract.McpToolInputSchema;
import com.demo.investments_wallet.mcp.tool.contract.McpToolResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;


@Slf4j
@Component
public class BuyAssetTool implements McpToolDefinition {

    private final PortfolioOperationService portfolioOperationService;

    public BuyAssetTool(PortfolioOperationService portfolioOperationService) {
        this.portfolioOperationService = portfolioOperationService;
    }

    @Override
    public McpToolData getData() {
        return McpToolData.builder()
                .name("buy_asset")
                .title("Buy Asset")
                .description("Tool to buy an investment asset.")
                .inputSchema(buildInputSchema())
                .build();
    }

    private McpToolInputSchema buildInputSchema() {
        McpToolInputSchema inputSchema = McpToolInputSchema.builder()
                .isRequired(true)
                .type("object")
                .build();

        inputSchema.addProperty("ticker", "Ticker symbol of the asset to buy.", "string", true);
        inputSchema.addProperty("quantity", "Quantity of the asset to buy.", "number", true);

        return inputSchema;
    }


    @Override
    public McpToolResponse execute(Map<String, Object> parameters) {
        log.info("Executing Tool: {}. Input data: {}", BuyAssetTool.class, parameters.toString());
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

            BuyAssetRequestDto request = BuyAssetRequestDto.builder()
                    .assetCode(ticker)
                    .quantity(quantityDecimal)
                    .build();

            AssetOperationResponseDto response = this.portfolioOperationService.buyAsset(request);
            responseMap = response.toMap();
            return McpToolResponse.success(responseMap);
        } catch (Exception ex) {
            log.error("Error executing tool: {}. Message: {}", BuyAssetTool.class, ex.getMessage());

            String errorMessage = ex.getMessage();
            responseMap.put("error", errorMessage);

            return McpToolResponse.failure(responseMap);
        }
    }

}
