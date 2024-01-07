package org.psota.taskmanagementbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "org.psota.taskmanagementbe.persistence.entity")
@EnableJpaRepositories(basePackages = "org.psota.taskmanagementbe.persistence.dao")
@ComponentScan(basePackages = {"org.psota.taskmanagementbe.mapper", "org.psota.taskmanagementbe"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
