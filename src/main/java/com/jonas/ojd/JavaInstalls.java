package com.jonas.ojd;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class JavaInstalls {

    public final static List<JavaInstall> LIST = List.of(
            new JavaInstall("oracle-jdk17", "Java 17", "Oracle", "C:/Program Files/Java/jdk-17.0.2/"));
    public final static JavaInstall DEFAULT = LIST.get(0);

    public static Collection<JavaInstall> all() {
        return LIST;
    }

    public static JavaInstall get(String key) {
        return LIST.stream().filter(i -> Objects.equals(i.key(), key)).findFirst().orElseThrow();
    }
}
