package by.bntu.diploma.diagram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "by.bntu.diploma.diagram")
public class Boot {

    public static void main(String[] args) {
        SpringApplication.run(Boot.class, args).getBeanDefinitionNames();
    }

}
