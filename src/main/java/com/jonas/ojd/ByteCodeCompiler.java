package com.jonas.ojd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Slf4j
public class ByteCodeCompiler {

    private final static Path ROOT;

    private final JavaBin javaBin = new JavaBin(JavaBin.DEFAULT);

    static {
        ROOT = Paths.get("target/ojd");
        try {
            Files.createDirectories(ROOT);
        } catch (FileAlreadyExistsException e) {
            log.info("Root dir already exists: {}", ROOT);
        } catch (IOException e) {
            throw new CreateCompileRootDirException(e);
        }
    }

    public String compile(TempJavaFile tempJavaFile) throws IOException, InterruptedException {
        try {
            int i;
            String targetPathString = tempJavaFile.getFilePathString();
            log.info("Compile temp file: {}", targetPathString);
            i = javaBin.compile(tempJavaFile);
            log.info("Compile return: {}", i);
            ByteCodeOutput output = new ByteCodeOutput();
            i = javaBin.disassembles(tempJavaFile, output);
            log.info("Disassembles return: {}", i);
            return output.getByteCode();
        } finally {
            tempJavaFile.remove();
        }
    }
}
