package com.jonas.ojd;

import java.util.List;

public class JavaInstalls {

    public final static List<JavaInstall> LIST = List.of(new JavaInstall("Oracle Java 17", "C:/Program Files/Java/jdk-17.0.2/"));
    public final static JavaInstall DEFAULT = LIST.get(0);

    public static List<JavaInstall> all() {
        return LIST;
    }
}
