package com.demo.investments_wallet.mcp.tool;

import com.demo.investments_wallet.domain.service.TransactionHistoryService;
import com.demo.investments_wallet.dto.TransactionHistoryEntryDto;
import com.demo.investments_wallet.dto.TransactionHistoryFilterRequestDto;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetTransactionHistoryTool {

    private final TransactionHistoryService transactionHistoryService;

    public GetTransactionHistoryTool(TransactionHistoryService transactionHistoryService) {
        this.transactionHistoryService = transactionHistoryService;
    }

    public List<TransactionHistoryEntryDto> execute(TransactionHistoryFilterRequestDto filter) {
        return transactionHistoryService.getTransactionHistory(filter);
    }
}
