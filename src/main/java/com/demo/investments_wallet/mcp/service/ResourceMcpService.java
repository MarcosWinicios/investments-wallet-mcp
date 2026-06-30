package com.demo.investments_wallet.mcp.service;

import com.demo.investments_wallet.mcp.resource.contract.McpResourceDefinition;
import com.demo.investments_wallet.mcp.to.ResourceDataResumeTo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceMcpService {


    private final List<McpResourceDefinition> availableResources;

    public ResourceMcpService(List<McpResourceDefinition> availableResources) {
        this.availableResources = availableResources;
    }

    public List<ResourceDataResumeTo> getAvailableResources() {
        return availableResources.stream()
                .map(resource -> new ResourceDataResumeTo(resource.data()))
                .toList();
    }
}
