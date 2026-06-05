package com.demo.investments_wallet.domain.entity;

import com.demo.investments_wallet.domain.types.AssetCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_portfolio_position")
public class PortfolioPositionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "asset_code", nullable = false, unique = true, length = 50)
    private String assetCode;

    @Column(name = "asset_name", nullable = false, length = 150)
    private String assetName;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_category", nullable = false, length = 30)
    private AssetCategory assetCategory;

    @Column(name = "quantity", nullable = false, precision = 19, scale = 8)
    private BigDecimal quantity;

    @Column(name = "average_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal averagePrice;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @SuppressWarnings("unused")
    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @SuppressWarnings("unused")
    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
