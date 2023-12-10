package com.jonas.ojd;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Validated
public class CompileController {

    private final ByteCodeCompiler byteCodeCompiler;

    @PostMapping("/compile")
    public String compile(@RequestBody CompileRequest request, Model model)
            throws IOException {
        List<CommandOutput> outputs = new ArrayList<>(request.javaSelects().size());
        try {
            for (CompileRequest.JavaSelect javaSelect : request.javaSelects()) {
                compile(byteCodeCompiler, javaSelect, request, outputs);
            }
        } catch (InterruptedException e) {
            return "interrupted";
        }
        model.addAttribute("outputs", outputs);
        model.addAttribute("code", request.javaCode());
        return "compile";
    }

    private static void compile(ByteCodeCompiler compiler, CompileRequest.JavaSelect javaSelect,
                                CompileRequest request, Collection<CommandOutput> outputs)
            throws IOException, InterruptedException {
        TempJavaFile tempJavaFile = TempJavaFile.create(request.mainClass(), request.javaCode());
        JavaInstall javaInstall = JavaInstalls.get(javaSelect.javaInstallSelect());
        CommandOutput output;
        output = compiler.compile(javaInstall, tempJavaFile, javaSelect.sourceVersion(),
                request.verbose() != null && request.verbose());
        outputs.add(output);
    }
}
