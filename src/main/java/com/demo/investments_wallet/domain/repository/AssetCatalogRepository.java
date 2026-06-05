package com.demo.investments_wallet.domain.repository;

import com.demo.investments_wallet.domain.entity.AssetEntity;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class AssetCatalogRepository{


    public List<AssetEntity> findAll() {
        return CatalogMock.findAll();
    }

    public AssetEntity findByCode(String code) {
        return CatalogMock.findByCode(code);
    }

}
