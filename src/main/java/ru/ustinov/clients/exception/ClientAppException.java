package ru.ustinov.clients.exception;

import lombok.Getter;

@Getter
public class ClientAppException extends RuntimeException {

    private final String reason;

    public ClientAppException(String reason) {
        this.reason = reason;
    }
}
