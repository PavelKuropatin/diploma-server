package by.bntu.diploma.diagram.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "by.bntu.diploma.diagram.domain")
@EnableJpaRepositories(basePackages = "by.bntu.diploma.diagram.repository")
@SpringBootApplication(scanBasePackages = {"by.bntu.diploma.diagram.service", "by.bntu.diploma.diagram.web"})
public class Boot {

    public static void main(String[] args) {
        SpringApplication.run(Boot.class, args);
    }
}
