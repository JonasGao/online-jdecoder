package com.jonas.ojd;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 开发使用。用来预览一些不是很方便能打开的页面
 */
@Controller
public class PreviewController {

    @GetMapping("/preview")
    public String preview(@RequestParam String name) {
        return name;
    }
}
