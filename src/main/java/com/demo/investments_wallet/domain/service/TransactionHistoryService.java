package com.demo.investments_wallet.domain.service;

import com.demo.investments_wallet.domain.entity.TransactionHistoryEntity;
import com.demo.investments_wallet.domain.repository.TransactionHistoryRepository;
import com.demo.investments_wallet.dto.TransactionHistoryEntryDto;
import com.demo.investments_wallet.dto.TransactionHistoryFilterRequestDto;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.demo.investments_wallet.domain.repository.spec.TransactionHistorySpecs.withAssetCode;
import static com.demo.investments_wallet.domain.repository.spec.TransactionHistorySpecs.withEndDate;
import static com.demo.investments_wallet.domain.repository.spec.TransactionHistorySpecs.withOperationType;
import static com.demo.investments_wallet.domain.repository.spec.TransactionHistorySpecs.withStartDate;

@Service
public class TransactionHistoryService extends DomainLogger{

    private final TransactionHistoryRepository transactionHistoryRepository;

    public TransactionHistoryService(TransactionHistoryRepository transactionHistoryRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    public List<TransactionHistoryEntryDto> getTransactionHistory(TransactionHistoryFilterRequestDto filter) {
        List<TransactionHistoryEntity> entities = new ArrayList<>();

        if(filter == null){
            logDebug("getTransactionHistory", "No filters specified");
            entities = transactionHistoryRepository.findAll();
            return this.convertEntityToDto(entities);
        }

        Specification<TransactionHistoryEntity> spec =  this.buildFilterSpec(filter);

        entities = transactionHistoryRepository.findAll(
                spec,
                Sort.by(Sort.Direction.ASC, "operationTimestamp")
        );

        return this.convertEntityToDto(entities);
    }

    private Specification<TransactionHistoryEntity> buildFilterSpec(TransactionHistoryFilterRequestDto filter) {
        return Specification.where(
                    withAssetCode(filter.assetCode())
                )
                .and(withOperationType(filter.operationType()))
                .and(withStartDate(filter.startDate()))
                .and(withEndDate(filter.endDate()));
    }

    private List<TransactionHistoryEntryDto> convertEntityToDto(List<TransactionHistoryEntity> entities) {
        List<TransactionHistoryEntryDto> list = entities.stream()
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

        logDebug ("convertEntityToDto", "Founded " + entities.size() + " transactions...");

        return list;
    }
}
