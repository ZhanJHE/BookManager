package com.rabbiter.bms.exception;


/**
 * 操作失败异常
 */
public class OperationFailureException extends RuntimeException{
    public OperationFailureException() {
    }

    public OperationFailureException(String message) {
        super(message);
    }
}