package com.demo.investments_wallet.domain.service;

import com.demo.investments_wallet.domain.entity.AssetEntity;
import com.demo.investments_wallet.domain.entity.PortfolioPositionEntity;
import com.demo.investments_wallet.domain.repository.PortfolioPositionRepository;
import com.demo.investments_wallet.domain.types.AssetCategory;
import com.demo.investments_wallet.dto.PortfolioPositionViewDto;
import com.demo.investments_wallet.dto.PortfolioSummaryResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class PortfolioQueryService extends DomainLogger{

    private final PortfolioPositionRepository portfolioPositionRepository;
    private final AssetCatalogService assetCatalogService;

    public PortfolioQueryService(
            PortfolioPositionRepository portfolioPositionRepository,
            AssetCatalogService assetCatalogService
    ) {
        this.portfolioPositionRepository = portfolioPositionRepository;
        this.assetCatalogService = assetCatalogService;
    }

    public PortfolioSummaryResponseDto getPortfolio() {
        this.logStartMethod("getPortfolio");

        List<PortfolioPositionEntity> positions = portfolioPositionRepository.findAllActivePositions();
        List<PortfolioPositionViewDto> views = new ArrayList<>();
        Map<AssetCategory, BigDecimal> categoryValues = new EnumMap<>(AssetCategory.class);

        BigDecimal totalPortfolioValue = BigDecimal.ZERO;

        for (PortfolioPositionEntity entity : positions) {
            AssetEntity asset = assetCatalogService.getAsset(entity.getAssetCode());
            BigDecimal referencePrice = asset.getPrice().setScale(2, RoundingMode.HALF_UP);
            BigDecimal positionValue = entity.getQuantity().multiply(referencePrice).setScale(2, RoundingMode.HALF_UP);

            views.add(new PortfolioPositionViewDto(
                    entity.getAssetCode(),
                    entity.getAssetName(),
                    entity.getAssetCategory(),
                    entity.getQuantity(),
                    entity.getAveragePrice(),
                    referencePrice,
                    positionValue
            ));

            totalPortfolioValue = totalPortfolioValue.add(positionValue);
            categoryValues.merge(entity.getAssetCategory(), positionValue, BigDecimal::add);
        }

        Map<AssetCategory, BigDecimal> allocation = new EnumMap<>(AssetCategory.class);
        for (Map.Entry<AssetCategory, BigDecimal> entry : categoryValues.entrySet()) {
            BigDecimal percent = totalPortfolioValue.compareTo(BigDecimal.ZERO) == 0
                    ? BigDecimal.ZERO
                    : entry.getValue().multiply(new BigDecimal("100"))
                    .divide(totalPortfolioValue, 2, RoundingMode.HALF_UP);
            allocation.put(entry.getKey(), percent);
        }

        PortfolioSummaryResponseDto result =
                new PortfolioSummaryResponseDto(views, totalPortfolioValue.setScale(2, RoundingMode.HALF_UP), allocation);

        this.logEndMethod("getPortfolio", result);
        return result;
    }
}
