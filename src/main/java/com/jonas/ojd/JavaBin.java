package com.jonas.ojd;

import lombok.Data;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
public class JavaBin {

    public final static String DEFAULT = "C:/Program Files/Java/jdk-17.0.2/";

    private final Path bin;
    private final Path javac;
    private final Path javap;

    public JavaBin(String path) {
        Path bin = Paths.get(path).resolve("bin");
        this.bin = bin;
        this.javac = bin.resolve("javac.exe");
        this.javap = bin.resolve("javap.exe");
    }

    public int compile(TempJavaFile javaFile) throws IOException, InterruptedException {
        Process start = new ProcessBuilder(javac.toString(), javaFile.getFilePathString())
                .inheritIO()
                .start();
        return start.waitFor();
    }

    public int disassembles(TempJavaFile javaFile, ByteCodeOutput byteCodeOutput) throws IOException, InterruptedException {
        Process start = new ProcessBuilder(javap.toString(), "-c", javaFile.getClassFilePathString())
                .start();
        try (InputStream in = start.getInputStream();
             ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            for (int length; (length = in.read(buffer)) != -1; ) {
                result.write(buffer, 0, length);
            }
            byteCodeOutput.setByteCode(result.toString(StandardCharsets.UTF_8));
            return start.waitFor();
        }
    }
}
