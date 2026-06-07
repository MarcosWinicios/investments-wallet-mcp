package com.demo.investments_wallet.mcp.tool.contract;

import io.modelcontextprotocol.server.McpServerFeatures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public interface McpToolDefinition {

    McpToolData getData();

    McpToolResponse execute(Map<String, Object> parameters);

    default void logStart(){
        Logger log = LoggerFactory.getLogger(McpToolDefinition.class);
        log.info("");
        log.info("Tool[{}] - execution start...", this.getData().name());
    }

    default void logFinish(){
        Logger log = LoggerFactory.getLogger(McpToolDefinition.class);
        log.info("Tool[{}] - execution finish...", this.getData().name());
        log.info("");
    }

    default void logError(Throwable ex){
        Logger log = LoggerFactory.getLogger(McpToolDefinition.class);
        log.error("Tool[{}] - execution error: {}", this.getData().name(), ex.getMessage());
    }

    default void  logInfo(String info){
        Logger log = LoggerFactory.getLogger(McpToolDefinition.class);
        log.info("Tool[{}] - info: {}", this.getData().name(),  info);
    }

    default void logDebug(String debug){
        Logger log = LoggerFactory.getLogger(McpToolDefinition.class);
        log.debug("Tool[{}] - debug: {}", this.getData().name(), debug);
    }



    default McpServerFeatures.SyncToolSpecification build(){
        return ToolSpecificationBuilder.build(
                this
        );
    }
}
