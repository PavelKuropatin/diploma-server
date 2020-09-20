package by.bntu.constructor.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
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
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .maxAge(3600)
                .allowedHeaders("authorization", "content-type", "xsrf-token")
                .exposedHeaders("xsrf-token")
        ;
    }
}
