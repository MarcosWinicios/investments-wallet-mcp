package com.demo.investments_wallet.mcp;

import com.demo.investments_wallet.mcp.resource.contract.McpResourceData;
import com.demo.investments_wallet.mcp.resource.contract.McpResourceDefinition;
import com.demo.investments_wallet.mcp.to.ResourceDataResumeTo;
import com.demo.investments_wallet.mcp.to.ToolDataResumeTo;
import com.demo.investments_wallet.mcp.tool.contract.McpToolData;
import com.demo.investments_wallet.mcp.tool.contract.McpToolDefinition;
import com.demo.investments_wallet.utils.JsonUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class McpService {


    private final List<McpResourceDefinition> availableResources;
    private final List<McpToolDefinition> availableTools;
    private final JsonUtil jsonUtil;

    public McpService(List<McpResourceDefinition> availableResources, List<McpToolDefinition> availableTools, JsonUtil jsonUtil) {
        this.availableResources = availableResources;
        this.availableTools = availableTools;
        this.jsonUtil = jsonUtil;
    }

    public List<ResourceDataResumeTo> getAvailableResources() {
        return availableResources.stream()
                .map(resource -> new ResourceDataResumeTo(resource.data()))
                .toList();
    }

    public List<ToolDataResumeTo> getAvailableTools(){
        List<ToolDataResumeTo> resultList = new ArrayList<>();

        this.availableTools.forEach(tool -> {
            resultList.add(this.toolResumeFromDefinition(tool));
        });

        return resultList;
    }

    public List<McpToolData> getAvailableToolsData(){

        return this.availableTools.stream()
                .map(McpToolDefinition::getData)
                .toList();
    }

    public ToolDataResumeTo getToolDetail(String name){
        McpToolDefinition tool = this.availableTools.stream()
                .filter(t -> t.getData().name().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tool not found: " + name));

        return toolResumeFromDefinition(tool);
    }

    private ToolDataResumeTo toolResumeFromDefinition(McpToolDefinition tool){
        ToolDataResumeTo response = ToolDataResumeTo.builder()
                .name(tool.getData().name())
                .title(tool.getData().title())
                .description(tool.getData().description())
                .build();
        try {
            String inputSchemaJson = this.jsonUtil.toJson(tool.getData().inputSchema());
            response.setInputSchema(inputSchemaJson);
        } catch (Exception e) {
            response.setInputSchema("Error serializing input schema: " + e.getMessage());
        }
        return response;
    }
}
