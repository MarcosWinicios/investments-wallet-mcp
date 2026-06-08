package com.demo.investments_wallet.domain.service;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class DomainLogger {

    public void logInfo(String method, String message) {
        log.info("Domain[{}] - {}", method, message);
    }
    public void logError(String method, String message) {
       log.error("Domain[{}] - error: {}", method, message);
    }

    public void logDebug(String method, String message) {
        log.debug("Domain[{}] - {}", method, message);
    }

    public void logStartMethod(String method){
        this.logInfo(method, "start...");
    }

    public void logStartMethod(String method, Object arg) {
        this.logInfo(method, "start...");
        this.logDebug(method, "args: " + arg.toString());
    }

    public void logEndMethod(String method) {
        this.logInfo(method, "end...");
    }

    public void logEndMethod(String method, Object response){
        this.logInfo(method, "end...");
        this.logDebug(method, "response: " + response.toString());
    }
}
