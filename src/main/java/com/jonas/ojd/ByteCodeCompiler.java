package com.jonas.ojd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class ByteCodeCompiler {

    public CommandOutput compile(JavaInstall javaInstall, TempJavaFile tempJavaFile, int sourceVersion,
                                 boolean verbose)
            throws IOException, InterruptedException {
        JavaBin javaBin = new JavaBin(javaInstall);
        CommandOutput output = new CommandOutput();
        try {
            int i;
            String targetPathString = tempJavaFile.getFilePathString();
            log.info("Compile temp file: {}", targetPathString);
            i = javaBin.compile(tempJavaFile, sourceVersion, output);
            log.info("Compile return: {}", i);
            if (i != 0) {
                output.setExitValue(i);
                return output;
            }
            i = javaBin.disassembles(tempJavaFile, output, verbose);
            log.info("Disassembles return: {}", i);
            output.setExitValue(i);
            return output;
        } finally {
            tempJavaFile.remove();
        }
    }
}
