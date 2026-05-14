package com.example.Agencia_Viajes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.Agencia_Viajes", "controller"})
public class AgenciaViajesApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgenciaViajesApplication.class, args);
    }
}