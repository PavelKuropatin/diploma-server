package by.bntu.constructor.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud_name}")
    private String cloudinaryCloudName;

    @Value("${cloudinary.api_key}")
    private String cloudinaryApiKey;

    @Value("${cloudinary.api_secret}")
    private String cloudinaryApiSecret;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(Cloudinary.asMap(
                "cloud_name", cloudinaryCloudName,
                "api_key", cloudinaryApiKey,
                "api_secret", cloudinaryApiSecret));

    }
}
