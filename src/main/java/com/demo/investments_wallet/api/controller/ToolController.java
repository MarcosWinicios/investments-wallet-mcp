package com.demo.investments_wallet.api.controller;

import com.demo.investments_wallet.mcp.service.ToolMcpService;
import com.demo.investments_wallet.mcp.to.ToolDataResumeTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tools")
public class ToolController {

    private final ToolMcpService toolMcpService;

    public ToolController(ToolMcpService toolMcpService) {
        this.toolMcpService = toolMcpService;
    }

    @GetMapping
    public List<ToolDataResumeTo> listAll(){
        return this.toolMcpService.getAvailableTools();
    }

}
