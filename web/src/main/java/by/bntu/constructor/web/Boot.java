package by.bntu.constructor.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = "by.bntu.constructor")
@EntityScan(basePackages = "by.bntu.constructor.domain")
@EnableJpaRepositories(basePackages = "by.bntu.constructor.repository")
@SpringBootApplication(scanBasePackages = {"by.bntu.constructor.service", "by.bntu.constructor.web"})
public class Boot {

    public static void main(String[] args) {
        SpringApplication.run(Boot.class, args);
    }
}
