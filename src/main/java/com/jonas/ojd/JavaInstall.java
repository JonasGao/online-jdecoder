package com.jonas.ojd;

import org.springframework.util.Assert;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public record JavaInstall(int version, String key, String name, String group, String path) {

    public String fullName() {
        return String.format("%-10s - %s", group, name);
    }

    public void validate() {
        Assert.hasText(key, "JavaInstall 配置缺少 Key");
        Assert.hasText(name, "JavaInstall 配置缺少 Name");
        Assert.hasText(group, "JavaInstall 配置缺少 Group");
        Assert.hasText(path, "JavaInstall 配置缺少 Path");
        Path path = Paths.get(this.path).resolve("bin");
        Assert.state(Files.exists(path), "JavaInstall 配置的 Path 不合法，目录不存在或缺少 bin 目录");
    }
}
