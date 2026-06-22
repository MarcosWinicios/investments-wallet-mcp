package com.demo.investments_wallet.mcp.prompt.implementation;

import com.demo.investments_wallet.mcp.prompt.contract.McpPromptData;
import com.demo.investments_wallet.mcp.prompt.contract.McpPromptDefinition;
import org.springframework.stereotype.Component;

@Component
public class WalletSummary implements McpPromptDefinition {

    @Override
    public McpPromptData getData() {

        String text = """
                You are an investor specialist.
                Make a summary of my wallet.
                
                - For this, use the "get_portfolio" mcp tool like data source.
                - Always answer in Portuguese language.
                """;

        return new McpPromptData(
                "wallet_summary",
                "Wallet summary",
                "Return a simple summary of the investments wallet.",
                text,
                "Your Summary wallet is ready"
        );
    }


}
