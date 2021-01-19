package com.fabio.projetodofabio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ProjetoDoFabioApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetoDoFabioApplication.class, args);
    }

}
