package com.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by LX on 2017/7/19.
 */
@SpringBootApplication
@EnableAutoConfiguration()
@ComponentScan("com")
public class Application {
    //lx5548
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
