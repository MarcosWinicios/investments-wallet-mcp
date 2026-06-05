package com.demo.investments_wallet.domain.service;

import com.demo.investments_wallet.domain.entity.AssetEntity;
import com.demo.investments_wallet.domain.repository.AssetCatalogRepository;
import com.demo.investments_wallet.domain.exception.DomainValidationException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AssetCatalogService {

    private final AssetCatalogRepository assetCatalogRepository;

    private final Map<String, AssetEntity> catalog = new LinkedHashMap<>();

    public AssetCatalogService(AssetCatalogRepository assetCatalogRepository){
        this.assetCatalogRepository = assetCatalogRepository;
    }

    private void loadCatalog(){
        List<AssetEntity> assets = assetCatalogRepository.findAll();
        assets.forEach(asset -> catalog.put(asset.getCode(), asset));
    }

    public List<AssetEntity> getAllAssets() {
        if (catalog.isEmpty()) {
            loadCatalog();
        }
        return List.copyOf(catalog.values());
    }

    public AssetEntity getAsset(String assetCode) {
        AssetEntity entry = catalog.get(normalizeCode(assetCode));
        if (entry == null) {
            throw new DomainValidationException("Unsupported asset code: " + assetCode);
        }
        return entry;
    }

    private String normalizeCode(String code) {
        return code == null ? "" : code.trim().toUpperCase(Locale.ROOT);
    }
}
