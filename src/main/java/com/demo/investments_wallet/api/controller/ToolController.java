package com.demo.investments_wallet.api.controller;

import com.demo.investments_wallet.mcp.McpService;
import com.demo.investments_wallet.mcp.resource.contract.McpResourceData;
import com.demo.investments_wallet.mcp.to.ToolDataResumeTo;
import com.demo.investments_wallet.mcp.tool.ToolService;
import com.demo.investments_wallet.mcp.tool.contract.McpToolData;
import com.demo.investments_wallet.mcp.tool.contract.McpToolResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mcp/tools")
public class ToolController {

    private final McpService mcpService;
    private final ToolService toolService;

    public ToolController(McpService mcpService, ToolService toolService) {
        this.mcpService = mcpService;
        this.toolService = toolService;
    }

    @GetMapping
    public List<ToolDataResumeTo> listAll(){
        return this.mcpService.getAvailableTools();
    }

    @GetMapping("/all")
    public List<McpToolData> list(){
        return this.mcpService.getAvailableToolsData();
    }

    @PostMapping("/buyAsset")
    public ResponseEntity<McpToolResponse> save(@RequestBody Map<String, Object> params){
        McpToolResponse save = toolService.save(params);
        return ResponseEntity.ok(save);
    }
}
