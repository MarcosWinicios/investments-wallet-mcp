package com.demo.investments_wallet.api.controller;

import com.demo.investments_wallet.mcp.prompt.contract.McpPromptData;
import com.demo.investments_wallet.mcp.service.PromptMcpService;
import com.demo.investments_wallet.mcp.service.ToolMcpService;
import com.demo.investments_wallet.mcp.to.ToolDataResumeTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/prompts")
public class PromptController {

    private final PromptMcpService promptMcpService;


    public PromptController(PromptMcpService promptMcpService) {
        this.promptMcpService = promptMcpService;
    }

    @GetMapping
    public List<McpPromptData> listAll(){
        return this.promptMcpService.getAvailablePrompts();
    }
}
