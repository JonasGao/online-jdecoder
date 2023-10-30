package com.jonas.ojd;

import lombok.Data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

@Data
public class JavaBin {

    private final static boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().contains("win");
    private final static String JAVAC = IS_WINDOWS ? "javac.exe" : "javac";
    private final static String JAVAP = IS_WINDOWS ? "javap.exe" : "javap";

    private final Path bin;
    private final Path javac;
    private final Path javap;

    public JavaBin(JavaInstall javaInstall) {
        Path bin = Paths.get(javaInstall.path()).resolve("bin");
        this.bin = bin;
        this.javac = bin.resolve(JAVAC);
        this.javap = bin.resolve(JAVAP);
    }

    public int compile(TempJavaFile javaFile, CommandOutput compileOutput) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(javac.toString(),
                "-J-Dfile.encoding=UTF-8",
                javaFile.getFilePathString());
        Process start = processBuilder.start();
        try (InputStream in = start.getInputStream();
             InputStream es = start.getErrorStream();
             ByteArrayOutputStream ir = new ByteArrayOutputStream();
             ByteArrayOutputStream er = new ByteArrayOutputStream()) {
            byte[] ib = new byte[1024];
            byte[] eb = new byte[1024];
            for (int length; (length = in.read(ib)) != -1; ) {
                ir.write(ib, 0, length);
            }
            for (int length; (length = es.read(eb)) != -1; ) {
                er.write(eb, 0, length);
            }
            Charset charset = StandardCharsets.UTF_8;
            compileOutput.setOutput(
                    ir.toString(charset),
                    er.toString(charset));
            return start.waitFor();
        }
    }

    public int disassembles(TempJavaFile javaFile, CommandOutput byteCodeOutput, boolean verbose) throws IOException, InterruptedException {
        List<String> command = new LinkedList<>();
        command.add(javap.toString());
        if (verbose) {
            command.add("-verbose");
        }
        command.add("-J-Dfile.encoding=UTF-8");
        command.add("-c");
        command.add(javaFile.getClassFilePathString());
        Process start = new ProcessBuilder(command)
                .start();
        try (InputStream in = start.getInputStream();
             ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            for (int length; (length = in.read(buffer)) != -1; ) {
                result.write(buffer, 0, length);
            }
            byteCodeOutput.setOutput(result.toString(StandardCharsets.UTF_8));
            return start.waitFor();
        }
    }
}
