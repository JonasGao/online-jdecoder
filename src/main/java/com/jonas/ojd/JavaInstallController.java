package com.jonas.ojd;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JavaInstallController {

    @GetMapping("/java-install")
    public List<JavaInstall> getJavaInstall() {
        return JavaInstalls.all();
    }
}
