package com.jonas.ojd;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ParserController {

    private final ByteCodeParser byteCodeParser;

    @PostMapping("/compile")
    public String compile(@RequestParam String mainClass, @RequestBody String codeBody) throws IOException {
        TempJavaFile tempJavaFile = TempJavaFile.create(mainClass, codeBody);
        try {
            return byteCodeParser.parse(tempJavaFile);
        } catch (InterruptedException e) {
            return "Interrupted";
        }
    }
}
