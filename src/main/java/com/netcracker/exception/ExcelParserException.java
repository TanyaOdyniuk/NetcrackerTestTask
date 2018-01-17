package com.netcracker.exception;

public class ExcelParserException extends RuntimeException {
    public ExcelParserException() {
        super();
    }

    public ExcelParserException(String message) {
        super(message);
    }

    public ExcelParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
