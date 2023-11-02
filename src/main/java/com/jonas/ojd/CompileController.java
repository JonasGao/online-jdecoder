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
                          @RequestParam String javaCode, @RequestParam Integer sourceVersion,
                          @RequestParam(required = false, defaultValue = "false") boolean verbose, Model model)
            throws IOException {
        TempJavaFile tempJavaFile = TempJavaFile.create(mainClass, javaCode);
        JavaInstall javaInstall = JavaInstalls.get(javaInstallSelect);
        CommandOutput output;
        try {
            output = byteCodeCompiler.compile(javaInstall, tempJavaFile, sourceVersion, verbose);
        } catch (InterruptedException e) {
            return "interrupted";
        }
        model.addAttribute("output", output);
        model.addAttribute("code", javaCode);
        return "compile";
    }
}
