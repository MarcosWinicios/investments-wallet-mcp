package com.demo.investments_wallet.mcp.resource.implementation;

import com.demo.investments_wallet.domain.entity.AssetEntity;
import com.demo.investments_wallet.domain.service.AssetCatalogService;
import com.demo.investments_wallet.mcp.resource.contract.McpResourceData;
import com.demo.investments_wallet.mcp.resource.contract.McpResourceDefinition;

import com.demo.investments_wallet.utils.JsonUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AvailableInvestmentsResource implements McpResourceDefinition {

    private final AssetCatalogService assetCatalogService;

    public AvailableInvestmentsResource(AssetCatalogService assetCatalogService) {
        this.assetCatalogService = assetCatalogService;
    }


    @Override
    public McpResourceData data() {

        List<AssetEntity> catalog = assetCatalogService.getAllAssets();
        String catalogJson = JsonUtil.toJson(catalog);

        return McpResourceData.builder()
                .uri("catalog://available-assets")
                .name("Available Assets")
                .description("List of available assets for investment")
                .mimeType("application/json")
                .content(catalogJson)
                .build();
    }
}

