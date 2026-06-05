package com.demo.investments_wallet.domain.entity;

import com.demo.investments_wallet.domain.types.AssetCategory;
import com.demo.investments_wallet.domain.types.OperationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_transaction_history")
public class TransactionHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type", nullable = false, length = 20)
    private OperationType operationType;

    @Column(name = "asset_code", nullable = false, length = 50)
    private String assetCode;

    @Column(name = "asset_name", nullable = false, length = 150)
    private String assetName;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_category", nullable = false, length = 30)
    private AssetCategory assetCategory;

    @Column(name = "quantity", nullable = false, precision = 19, scale = 8)
    private BigDecimal quantity;

    @Column(name = "unit_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "operation_timestamp", nullable = false)
    private LocalDateTime operationTimestamp;
}
