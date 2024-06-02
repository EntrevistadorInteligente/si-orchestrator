package com.entrevistador.orquestador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OrquestadorApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrquestadorApplication.class, args);
    }

}