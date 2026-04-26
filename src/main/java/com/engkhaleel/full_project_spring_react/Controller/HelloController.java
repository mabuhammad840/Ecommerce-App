package com.engkhaleel.full_project_spring_react.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello ")
    public String hello() {
        return "hello";
    }
}

