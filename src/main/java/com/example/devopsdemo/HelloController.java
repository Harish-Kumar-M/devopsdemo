package com.example.devopsdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/")
    public String home() {
        return helloService.getMessage();
    }

    @GetMapping("/greet")
    public String greetMessage()
    {
        return helloService.getGreetMessage();
    }
}