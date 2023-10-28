package com.jonas.ojd;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class CompileController {

    private final ByteCodeCompiler byteCodeCompiler;

    @PostMapping("/compile")
    public String compile(@RequestParam String javaInstallSelect, @RequestParam String mainClass,
                          @RequestParam String javaCode, Model model) throws IOException {
        TempJavaFile tempJavaFile = TempJavaFile.create(mainClass, javaCode);
        JavaInstall javaInstall = JavaInstalls.get(javaInstallSelect);
        String bytecode;
        try {
            bytecode = byteCodeCompiler.compile(javaInstall, tempJavaFile);
        } catch (InterruptedException e) {
            model.addAttribute("javaInstall", javaInstallSelect);
            model.addAttribute("mainClass", mainClass);
            model.addAttribute("javaCode", javaCode);
            return "interrupted";
        }
        model.addAttribute("bytecode", bytecode);
        model.addAttribute("code", javaCode);
        return "compile";
    }
}
