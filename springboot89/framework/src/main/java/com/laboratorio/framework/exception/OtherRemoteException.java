package com.laboratorio.framework.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OtherRemoteException extends RuntimeException {
    public OtherRemoteException(String message, Throwable cause) {
        super(message, cause);
        log.error(message);
    }
}