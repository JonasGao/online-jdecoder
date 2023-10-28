package com.jonas.ojd;

public record JavaInstall(String key, String name, String group, String path) {
    public String fullName() {
        return String.format("%-10s - %s", group, name);
    }
}
