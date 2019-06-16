package by.bntu.diagram.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = "by.bntu.diagram")
@EntityScan(basePackages = "by.bntu.diagram.domain")
@EnableJpaRepositories(basePackages = "by.bntu.diagram.repository")
@SpringBootApplication(scanBasePackages = {"by.bntu.diagram.service", "by.bntu.diagram.web"})
public class Boot {

    public static void main(String[] args) {
        SpringApplication.run(Boot.class, args);
    }
}
