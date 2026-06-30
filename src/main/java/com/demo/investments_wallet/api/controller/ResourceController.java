package com.demo.investments_wallet.api.controller;

import com.demo.investments_wallet.mcp.service.ResourceMcpService;
import com.demo.investments_wallet.mcp.to.ResourceDataResumeTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceMcpService resourceMcpService;

    public ResourceController(ResourceMcpService resourceMcpService) {
        this.resourceMcpService = resourceMcpService;
    }

    @GetMapping()
    public List<ResourceDataResumeTo> listAll() {
        return this.resourceMcpService.getAvailableResources();
    }
}
