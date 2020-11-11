package com.lightning.exceptions;

public class IllegalBoardPasswordLengthException extends RuntimeException {
    public IllegalBoardPasswordLengthException(String message) {
        super(message);
    }
}
