package com.demo.investments_wallet.domain.repository;

import com.demo.investments_wallet.domain.entity.AssetEntity;
import com.demo.investments_wallet.domain.types.AssetCategory;

import java.math.BigDecimal;
import java.util.List;



public class CatalogMock {

    public static List<AssetEntity> findAll() {
        List<AssetEntity> result = List.of(
            new AssetEntity("MXRF11", "Maxi Renda", AssetCategory.FII, new BigDecimal("10.45"), "MEDIUM", "Fictional real estate investment fund focused on income generation."),
            new AssetEntity("KNRI11", "Kinea Renda Imobiliaria", AssetCategory.FII, new BigDecimal("148.90"), "MEDIUM", "Fictional real estate investment fund focused on commercial properties."),
            new AssetEntity("HGLG11", "CSHG Logistica", AssetCategory.FII, new BigDecimal("162.35"), "LOW", "Fictional logistics-focused real estate investment fund."),
            new AssetEntity("PETR4", "Petrobras", AssetCategory.STOCK, new BigDecimal("38.20"), "HIGH", "Fictional stock representation inspired by the energy sector."),
            new AssetEntity("VALE3", "Vale", AssetCategory.STOCK, new BigDecimal("67.80"), "HIGH", "Fictional mining sector stock focused on commodity operations."),
            new AssetEntity("ITUB4", "Itau Unibanco", AssetCategory.STOCK, new BigDecimal("31.15"), "MEDIUM", "Fictional banking sector stock focused on retail financial services."),
            new AssetEntity("CDB110CDI", "CDB 110% CDI", AssetCategory.CDB, new BigDecimal("1000.00"), "LOW", "Fictional fixed income product indexed at 110% of CDI."),
            new AssetEntity("CDBPRE12", "CDB Prefixado 12%", AssetCategory.CDB, new BigDecimal("1000.00"), "LOW", "Fictional fixed income product with a 12% annual fixed rate."),
            new AssetEntity("CDBIPCA8", "CDB IPCA + 8%", AssetCategory.CDB, new BigDecimal("1000.00"), "LOW", "Fictional inflation-indexed fixed income product.")
        );

        return result;
    }

    public static AssetEntity findByCode(String code) {
        return findAll().stream()
            .filter(asset -> asset.getCode().equalsIgnoreCase(code))
            .findFirst()
            .orElse(null);
    }
}
