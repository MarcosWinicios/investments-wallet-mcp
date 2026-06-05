package com.demo.investments_wallet.api.controller;

import com.demo.investments_wallet.mcp.McpService;
import com.demo.investments_wallet.mcp.resource.contract.McpResourceData;
import com.demo.investments_wallet.mcp.to.ToolDataResumeTo;
import com.demo.investments_wallet.mcp.tool.contract.McpToolData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mcp/tools")
public class ToolController {

    private final McpService mcpService;

    public ToolController(McpService mcpService) {
        this.mcpService = mcpService;
    }

    @GetMapping
    public List<ToolDataResumeTo> listAll(){
        return this.mcpService.getAvailableTools();
    }

    @GetMapping("/all")
    public List<McpToolData> list(){
        return this.mcpService.getAvailableToolsData();
    }
}
