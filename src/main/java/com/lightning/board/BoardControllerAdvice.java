package com.lightning.board;

import com.lightning.common.ResponseMessage;
import com.lightning.exceptions.IllegalBoardPasswordLengthException;
import com.lightning.exceptions.IllegalBoardPasswordRuleException;
import com.lightning.exceptions.PasswordsNotSameException;
import com.lightning.exceptions.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class BoardControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseMessage illegalArgumentException(final IllegalArgumentException ex) {
        return new ResponseMessage(ex.getMessage());
    }

    @ExceptionHandler(IllegalBoardPasswordLengthException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseMessage illegalBoardPasswordLengthException(final IllegalBoardPasswordLengthException ex) {
        return new ResponseMessage(ex.getMessage());
    }

    @ExceptionHandler(IllegalBoardPasswordRuleException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseMessage illegalBoardPasswordRuleException(final IllegalBoardPasswordRuleException ex) {
        return new ResponseMessage(ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFound.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseMessage resourceNotFound(final ResourceNotFound ex) {
        return new ResponseMessage(ex.getMessage());
    }

    @ExceptionHandler(PasswordsNotSameException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseMessage passwordsNotSameException(final PasswordsNotSameException ex) {
        return new ResponseMessage(ex.getMessage());
    }
}
