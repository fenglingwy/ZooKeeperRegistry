package com.wy.service;

import com.wy.service.registry.ExecutorListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application  {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public ExecutorListener executorListener(){
        return new ExecutorListener();
    }

}
