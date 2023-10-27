package com.jonas.ojd;

import java.io.IOException;

public class CreateCompileRootDirException extends RuntimeException {
    public CreateCompileRootDirException(IOException e) {
        super(e);
    }
}
