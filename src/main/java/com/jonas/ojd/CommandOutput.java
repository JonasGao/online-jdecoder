package com.jonas.ojd;

import lombok.Data;

@Data
public class CommandOutput {
    private String output;
    private String error;
    private int exitValue;

    public void setOutput(String output, String error) {
        this.output = output;
        this.error = error;
    }

    public String printOutput() {
        if (exitValue == 0) {
            return output;
        }
        return error;
    }
}
