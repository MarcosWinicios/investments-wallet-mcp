package com.demo.investments_wallet.mcp.resource.implementation;

import com.demo.investments_wallet.domain.entity.AssetEntity;
import com.demo.investments_wallet.domain.service.AssetCatalogService;
import com.demo.investments_wallet.mcp.resource.contract.McpResourceData;
import com.demo.investments_wallet.mcp.resource.contract.McpResourceDefinition;

import com.demo.investments_wallet.utils.JsonUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AvailableInvestments implements McpResourceDefinition {

    private final AssetCatalogService assetCatalogService;
    private final JsonUtil jsonUtil;

    public AvailableInvestments(AssetCatalogService assetCatalogService, JsonUtil jsonUtil) {
        this.assetCatalogService = assetCatalogService;
        this.jsonUtil = jsonUtil;
    }


    @Override
    public McpResourceData data() {

        List<AssetEntity> catalog = assetCatalogService.getAllAssets();
        String catalogJson = jsonUtil.toJson(catalog);

        return McpResourceData.builder()
                .uri("catalog://available-assets")
                .name("Available Assets")
                .description("List of available assets for investment")
                .mimeType("application/json")
                .content(catalogJson)
                .build();
    }
}

