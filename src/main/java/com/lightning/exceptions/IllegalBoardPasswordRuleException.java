package com.lightning.exceptions;

public class IllegalBoardPasswordRuleException extends RuntimeException {
    public IllegalBoardPasswordRuleException(String message) {
        super(message);
    }
}
