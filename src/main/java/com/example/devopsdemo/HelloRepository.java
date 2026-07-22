package com.example.devopsdemo;

import org.springframework.stereotype.Repository;

@Repository
public class HelloRepository {

    public String getMessage() {
        return "Hello DevOps";
    }

    public String getGreetMessage()
    {
        return "Hey man !!! Hi";
    }
}
