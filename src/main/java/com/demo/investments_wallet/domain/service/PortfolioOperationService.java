package com.demo.investments_wallet.domain.service;

import com.demo.investments_wallet.domain.entity.AssetEntity;
import com.demo.investments_wallet.domain.entity.PortfolioPositionEntity;
import com.demo.investments_wallet.domain.entity.TransactionHistoryEntity;
import com.demo.investments_wallet.domain.types.OperationType;
import com.demo.investments_wallet.domain.exception.DomainValidationException;
import com.demo.investments_wallet.domain.repository.PortfolioPositionRepository;
import com.demo.investments_wallet.domain.repository.TransactionHistoryRepository;
import com.demo.investments_wallet.dto.AssetOperationResponseDto;
import com.demo.investments_wallet.dto.BuyAssetRequestDto;
import com.demo.investments_wallet.dto.SellAssetRequestDto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PortfolioOperationService {

    private static final BigDecimal ZERO = BigDecimal.ZERO;

    private final PortfolioPositionRepository portfolioPositionRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final AssetCatalogService assetCatalogService;

    public PortfolioOperationService(
            PortfolioPositionRepository portfolioPositionRepository,
            TransactionHistoryRepository transactionHistoryRepository,
            AssetCatalogService assetCatalogService
    ) {
        this.portfolioPositionRepository = portfolioPositionRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.assetCatalogService = assetCatalogService;
    }

    @Transactional
    public AssetOperationResponseDto buyAsset(BuyAssetRequestDto request) {
        if (request == null) {
            throw new DomainValidationException("Buy request cannot be null");
        }

        AssetEntity asset = assetCatalogService.getAsset(request.assetCode());
        BigDecimal unitPrice = asset.getPrice().setScale(2, RoundingMode.HALF_UP);

        BigDecimal quantity = request.quantity();
        BigDecimal totalAmount = request.totalAmount();

        if (isNonPositive(quantity) && isNonPositive(totalAmount)) {
            throw new DomainValidationException("Either positive quantity or positive total amount is required");
        }

        if (quantity == null || quantity.compareTo(ZERO) <= 0) {
            quantity = totalAmount.divide(unitPrice, 8, RoundingMode.HALF_UP);
        }

        if (totalAmount == null || totalAmount.compareTo(ZERO) <= 0) {
            totalAmount = quantity.multiply(unitPrice).setScale(2, RoundingMode.HALF_UP);
        }

        final BigDecimal resolvedQuantity = quantity;
        PortfolioPositionEntity position = portfolioPositionRepository.findByAssetCode(asset.getCode())
                .orElseGet(() -> newPosition(asset, resolvedQuantity, unitPrice));

        if (position.getId() != null) {
            BigDecimal oldQuantity = position.getQuantity();
            BigDecimal newQuantity = oldQuantity.add(quantity);
            BigDecimal weightedOldValue = oldQuantity.multiply(position.getAveragePrice());
            BigDecimal weightedNewValue = quantity.multiply(unitPrice);
            BigDecimal newAveragePrice = weightedOldValue.add(weightedNewValue)
                    .divide(newQuantity, 2, RoundingMode.HALF_UP);

            position.setQuantity(newQuantity);
            position.setAveragePrice(newAveragePrice);
        }

        PortfolioPositionEntity persisted = portfolioPositionRepository.saveAndFlush(position);
        recordTransaction(OperationType.BUY, asset, quantity, unitPrice, totalAmount);

        return new AssetOperationResponseDto(
                OperationType.BUY,
                asset.getCode(),
                asset.getName(),
                asset.getCategory(),
                quantity,
                totalAmount,
                persisted.getQuantity()
        );
    }

    @Transactional
    public AssetOperationResponseDto sellAsset(SellAssetRequestDto request) {
        if (request == null) {
            throw new DomainValidationException("Sell request cannot be null");
        }
        if (isNonPositive(request.quantity())) {
            throw new DomainValidationException("Sell quantity must be positive");
        }

        AssetEntity asset = assetCatalogService.getAsset(request.assetCode());
        PortfolioPositionEntity position = portfolioPositionRepository.findByAssetCode(asset.getCode())
                .orElseThrow(() -> new DomainValidationException("Asset is not present in portfolio: " + asset.getCode()));

        if (position.getQuantity().compareTo(request.quantity()) < 0) {
            throw new DomainValidationException("Insufficient quantity for asset: " + asset.getCode());
        }

        BigDecimal unitPrice = asset.getPrice().setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalAmount = request.quantity().multiply(unitPrice).setScale(2, RoundingMode.HALF_UP);
        BigDecimal updatedQuantity = position.getQuantity().subtract(request.quantity());

        if (updatedQuantity.compareTo(ZERO) == 0) {
            portfolioPositionRepository.delete(position);
        } else {
            position.setQuantity(updatedQuantity);
            portfolioPositionRepository.save(position);
        }

        recordTransaction(OperationType.SELL, asset, request.quantity(), unitPrice, totalAmount);

        return new AssetOperationResponseDto(
                OperationType.SELL,
                asset.getCode(),
                asset.getName(),
                asset.getCategory(),
                request.quantity(),
                totalAmount,
                updatedQuantity
        );
    }

    private PortfolioPositionEntity newPosition(
            AssetEntity asset,
            BigDecimal quantity,
            BigDecimal averagePrice
    ) {
        PortfolioPositionEntity entity = new PortfolioPositionEntity();
        entity.setAssetCode(asset.getCode());
        entity.setAssetName(asset.getName());
        entity.setAssetCategory(asset.getCategory());
        entity.setQuantity(quantity);
        entity.setAveragePrice(averagePrice);
        return entity;
    }

    private void recordTransaction(
            OperationType operationType,
            AssetEntity asset,
            BigDecimal quantity,
            BigDecimal unitPrice,
            BigDecimal totalAmount
    ) {
        TransactionHistoryEntity transaction = new TransactionHistoryEntity();
        transaction.setOperationType(operationType);
        transaction.setAssetCode(asset.getCode());
        transaction.setAssetName(asset.getName());
        transaction.setAssetCategory(asset.getCategory());
        transaction.setQuantity(quantity);
        transaction.setUnitPrice(unitPrice);
        transaction.setTotalAmount(totalAmount);
        transaction.setOperationTimestamp(LocalDateTime.now());
        transactionHistoryRepository.save(transaction);
    }

    private boolean isNonPositive(BigDecimal value) {
        return value == null || value.compareTo(ZERO) <= 0;
    }
}
