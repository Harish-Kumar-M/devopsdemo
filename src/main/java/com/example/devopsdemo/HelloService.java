package com.example.devopsdemo;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    HelloRepository helloRepository = new HelloRepository();
    public String getMessage() {
        return helloRepository.getMessage();
    }

    public String getGreetMessage()
    {
        return helloRepository.getGreetMessage();
    }
}
