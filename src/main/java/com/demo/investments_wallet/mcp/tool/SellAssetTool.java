package com.demo.investments_wallet.mcp.tool;

import com.demo.investments_wallet.domain.service.PortfolioOperationService;
import com.demo.investments_wallet.dto.AssetOperationResponseDto;
import com.demo.investments_wallet.dto.SellAssetRequestDto;
import org.springframework.stereotype.Component;

@Component
public class SellAssetTool {

    private final PortfolioOperationService portfolioOperationService;

    public SellAssetTool(PortfolioOperationService portfolioOperationService) {
        this.portfolioOperationService = portfolioOperationService;
    }

    public AssetOperationResponseDto execute(SellAssetRequestDto request) {
        return portfolioOperationService.sellAsset(request);
    }
}
