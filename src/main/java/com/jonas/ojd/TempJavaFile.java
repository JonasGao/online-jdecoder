package com.jonas.ojd;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Data
public class TempJavaFile {

    private final static Path ROOT;

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

    private final String mainClass;

    private final String code;

    private final Path filePath;

    private final Path classFilePath;

    public static TempJavaFile create(String mainClass, String code) throws IOException {
        Path targetFilePath = ROOT.resolve(mainClass + ".java");
        Path targetClassFilePath = ROOT.resolve(mainClass + ".class");
        TempJavaFile tempJavaFile = new TempJavaFile(mainClass, code, targetFilePath, targetClassFilePath);
        tempJavaFile.save();
        return tempJavaFile;
    }

    public void save() throws IOException {
        Path filePath = this.filePath;
        if (Files.exists(filePath)) {
            return;
        }
        Files.createFile(filePath);
        Files.writeString(filePath, code);
    }

    public String getFilePathString() {
        return this.filePath.toString();
    }

    public void remove() throws IOException {
        Path filePath = this.filePath;
        Path classFilePath = this.classFilePath;
        try {
            Files.delete(filePath);
        } catch (NoSuchFileException ignored) {
        }
        try {
            Files.delete(classFilePath);
        } catch (NoSuchFileException ignored) {
        }
        log.info("Remove temp java file and class file: {}, {}", filePath, classFilePath);
    }

    public String getClassFilePathString() {
        return this.classFilePath.toString();
    }
}
