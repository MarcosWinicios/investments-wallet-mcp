package com.demo.investments_wallet.mcp.prompt.implementation;

import com.demo.investments_wallet.mcp.prompt.contract.McpPromptData;
import com.demo.investments_wallet.mcp.prompt.contract.McpPromptDefinition;
import org.springframework.stereotype.Component;

@Component
public class AssetPosition implements McpPromptDefinition {

    @Override
    public McpPromptData getData() {

        String text = """
       Use the 'asset_position' mcp tool to find the asset position in my investment wallet.
       Target asset: %s.
       
       Always answer in Portuguese language.
       """;

        McpPromptData mcpPromptData = new McpPromptData(
                "find_asset_position",
                "Find asset position",
                "Return the asset position.",
                text,
                "Asset Position"
        );

        mcpPromptData.addArgument(
                "ticker",
                "Ticker",
                "Asset ticker code.",
                true);
        return mcpPromptData;
    }


}
