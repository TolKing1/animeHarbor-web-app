package org.tolking.animeharbor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "org.tolking.animeharbor.entities")
public class AnimeHarborApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimeHarborApplication.class, args);
    }

}
