package com.demo.investments_wallet.mcp.tool.implementation;

import com.demo.investments_wallet.domain.service.TransactionHistoryService;
import com.demo.investments_wallet.domain.types.OperationType;
import com.demo.investments_wallet.dto.TransactionHistoryEntryDto;
import com.demo.investments_wallet.dto.TransactionHistoryFilterRequestDto;
import com.demo.investments_wallet.mcp.tool.contract.McpToolData;
import com.demo.investments_wallet.mcp.tool.contract.McpToolDefinition;
import com.demo.investments_wallet.mcp.tool.contract.McpToolInputSchema;
import com.demo.investments_wallet.mcp.tool.contract.McpToolResponse;
import com.demo.investments_wallet.utils.DateUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GetTransactionHistoryTool implements McpToolDefinition {

    private final TransactionHistoryService transactionHistoryService;

    public GetTransactionHistoryTool(TransactionHistoryService transactionHistoryService) {
        this.transactionHistoryService = transactionHistoryService;
    }


    @Override
    public McpToolData getData() {
        return McpToolData.builder()
                .name("get_transaction_history")
                .title("Get transaction history")
                .description("Return the investments wallet transactions history.")
                .inputSchema(this.buildInputSchema())
                .build();
    }

    private McpToolInputSchema buildInputSchema() {
        McpToolInputSchema result = McpToolInputSchema.builder()
                .type("object")
                .isRequired(false)
                .build();

        result.addProperty(
                "ticker",
                "Asset Code",
                "string",
                false
        );
        result.addProperty(
                "operationType",
                "Operation Type. Available values: BUY | SELL",
                "string",
                false
        );

        result.addProperty(
                "startDate",
                "Initial date of range. Example: 2026-01-01",
                "string",
                false
        );

        result.addProperty(
                "endDate",
                "Final date of range. Example: 2026-01-30",
                "string",
                false
        );

        return result;
    }

    @Override
    public McpToolResponse execute(Map<String, Object> parameters) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            OperationType operationType = this.resolveOperationTypeParameters(String.valueOf(parameters.get("operationType")));
            LocalDateTime startDate = DateUtil.parseLocalDateTime(String.valueOf(parameters.get("startDate")));
            LocalDateTime endDate = DateUtil.parseLocalDateTime(String.valueOf(parameters.get("endDate")));
            endDate = DateUtil.toEndOfDay(endDate);

            TransactionHistoryFilterRequestDto filters = TransactionHistoryFilterRequestDto.builder()
                    .assetCode(this.resolveStringParameters(String.valueOf(parameters.get("ticker"))))
                    .operationType(operationType)
                    .startDate(startDate)
                    .endDate(endDate)
                    .build();

            boolean filtersIsNull = filtersIsNull(filters);


            List<TransactionHistoryEntryDto> transactionHistory =
                    this.transactionHistoryService.getTransactionHistory(filtersIsNull ?  null : filters);

            List<Map<String, Object>> resultList = transactionHistory.stream()
                    .map(TransactionHistoryEntryDto::toMap)
                    .toList();

            resultMap.put("transactionHistory", resultList);

            return McpToolResponse.success(resultMap);

        } catch (Exception ex) {
            this.logError(ex);
            resultMap.put("error", ex.getMessage());
            return McpToolResponse.failure(resultMap);
        }
    }

    private static boolean filtersIsNull(TransactionHistoryFilterRequestDto filters) {
        return filters.startDate() == null
                && filters.endDate() == null
                && filters.assetCode() == null
                && filters.operationType() == null;
    }

    private String resolveStringParameters(String parameter) {
        if (parameter == null) return null;
        if (parameter.isEmpty()) return null;
        if (parameter.isBlank()) return null;
        if("null".equalsIgnoreCase(parameter)) return null;

        return parameter;
    }

    private LocalDateTime resolveDateTimeParameters(String parameter) {
        if (this.resolveStringParameters(parameter) == null) return null;

        return DateUtil.parseLocalDateTime(parameter);
    }

    private OperationType resolveOperationTypeParameters(String parameter) {
        if (this.resolveStringParameters(parameter) == null) return null;

        return OperationType.fromString(parameter.toUpperCase());
    }
}
