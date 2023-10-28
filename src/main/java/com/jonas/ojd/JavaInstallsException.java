package com.jonas.ojd;

public class JavaInstallsException extends RuntimeException {
    public JavaInstallsException(String message) {
        super(message);
    }

    public JavaInstallsException(String message, RuntimeException e) {
        super(message, e);
    }
}
