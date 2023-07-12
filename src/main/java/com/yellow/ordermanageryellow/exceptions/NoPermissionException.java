package com.yellow.ordermanageryellow.exceptions;

public class NoPermissionException extends Exception {
    public NoPermissionException(String errorMessage){
        super(errorMessage);
    }
}
