package com.demo.investments_wallet.domain.service;

import com.demo.investments_wallet.domain.entity.TransactionHistoryEntity;
import com.demo.investments_wallet.domain.repository.TransactionHistoryRepository;
import com.demo.investments_wallet.dto.TransactionHistoryEntryDto;
import com.demo.investments_wallet.dto.TransactionHistoryFilterRequestDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TransactionHistoryService {

    private final TransactionHistoryRepository transactionHistoryRepository;

    public TransactionHistoryService(TransactionHistoryRepository transactionHistoryRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    public List<TransactionHistoryEntryDto> getTransactionHistory(TransactionHistoryFilterRequestDto filter) {
        TransactionHistoryFilterRequestDto safeFilter = filter == null
                ? new TransactionHistoryFilterRequestDto(null, null, null, null)
                : filter;

        List<TransactionHistoryEntity> entities = transactionHistoryRepository.findByFilters(
                safeFilter.assetCode(),
                safeFilter.operationType(),
                safeFilter.startDate(),
                safeFilter.endDate()
        );

        return entities.stream()
                .map(entity -> new TransactionHistoryEntryDto(
                        entity.getOperationType(),
                        entity.getAssetCode(),
                        entity.getAssetName(),
                        entity.getAssetCategory(),
                        entity.getQuantity(),
                        entity.getTotalAmount(),
                        entity.getOperationTimestamp()
                ))
                .toList();
    }
}
