package com.jonas.ojd;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ParserController {

    private final ByteCodeCompiler byteCodeCompiler;

    @PostMapping("/compile")
    public String compile(@RequestParam String javaInstallSelect, @RequestParam String mainClass,
                          @RequestParam String codeBody, Model model) throws IOException {
        TempJavaFile tempJavaFile = TempJavaFile.create(mainClass, codeBody);
        JavaInstall javaInstall = JavaInstalls.get(javaInstallSelect);
        try {
            return byteCodeCompiler.compile(javaInstall, tempJavaFile);
        } catch (InterruptedException e) {
            return "Interrupted";
        }
    }
}
