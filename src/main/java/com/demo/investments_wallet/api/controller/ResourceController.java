package com.demo.investments_wallet.api.controller;

import com.demo.investments_wallet.domain.entity.AssetEntity;
import com.demo.investments_wallet.domain.service.AssetCatalogService;
import com.demo.investments_wallet.mcp.McpService;
import com.demo.investments_wallet.mcp.to.ResourceDataResumeTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mcp/resources")
public class ResourceController {

    private final AssetCatalogService assetCatalogService;

    private final McpService mcpService;

    public ResourceController(AssetCatalogService assetCatalogService, McpService mcpService) {
        this.assetCatalogService = assetCatalogService;
        this.mcpService = mcpService;
    }

    @GetMapping()
    public List<ResourceDataResumeTo> listAll(){
        return this.mcpService.getAvailableResources();
    }

    @GetMapping("/assets-catalog")
    public List<AssetEntity> getAssetsCatalog(){
        return this.assetCatalogService.getAllAssets();
    }
}
