package com.jonas.ojd;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class JavaInstalls {

    private final static Map<String, JavaInstall> JAVA_MAP = new HashMap<>();

    private final static String CONF_NAME = "jdk.csv";

    public static void load() {
        Path confPath = Paths.get(CONF_NAME);
        log.info("加载jdk配置: {}", confPath.toAbsolutePath());
        if (!Files.exists(confPath)) {
            throw new JavaInstallsException("没有找到配置文件 " + CONF_NAME);
        }
        List<String> confLines;
        try {
            confLines = Files.readAllLines(confPath);
        } catch (IOException e) {
            throw new JavaInstallsException("读取配置文件失败");
        }
        try {
            for (String confLine : confLines) {
                String[] split = confLine.split(",");
                if (split.length != 4) {
                    throw new JavaInstallsException("解析配置文件失败: " + confLine);
                }
                JavaInstall javaInstall = new JavaInstall(split[0], split[1], split[2], split[3]);
                javaInstall.validate();
                JAVA_MAP.put(split[0], javaInstall);
            }
        } catch (JavaInstallsException je) {
            throw je;
        } catch (RuntimeException e) {
            throw new JavaInstallsException("解析配置文件失败", e);
        }
    }

    public static Collection<JavaInstall> all() {
        return JAVA_MAP.values();
    }

    public static JavaInstall get(String key) {
        JavaInstall javaInstall = JAVA_MAP.get(key);
        if (javaInstall == null) {
            throw new JavaInstallsException("没有找到 [" + key + "] JDK 配置");
        }
        return javaInstall;
    }
}
