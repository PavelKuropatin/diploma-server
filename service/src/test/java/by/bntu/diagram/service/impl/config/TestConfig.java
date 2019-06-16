package by.bntu.diagram.service.impl.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(value = "by.bntu.diagram.service")
@EntityScan(basePackages = "by.bntu.diagram.domain")
@EnableJpaRepositories(basePackages = "by.bntu.diagram.repository")
@Import({DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class TestConfig {
}