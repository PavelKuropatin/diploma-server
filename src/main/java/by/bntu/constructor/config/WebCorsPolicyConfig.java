package by.bntu.constructor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebCorsPolicyConfig implements WebMvcConfigurer {

    @Value("${cors.origin-hosts}")
    private String originHosts;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(originHosts.split(","))
                .allowCredentials(true)
                .allowedMethods("*")
                .maxAge(3600)
                .allowedHeaders("Authorization", "Content-Type", "xsrf-token", "Access-Control-Request-Method")
                .exposedHeaders("xsrf-token");
    }
}
