package org.flavorforge.flavorforge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "org.flavorforge.flavorforge.ai.client")
public class FlavorForgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlavorForgeApplication.class, args);
    }

}
