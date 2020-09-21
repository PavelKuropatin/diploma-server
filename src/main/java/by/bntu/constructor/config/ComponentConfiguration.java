package by.bntu.constructor.config;

import by.bntu.constructor.web.util.Format;
import by.bntu.constructor.web.util.converter.format.SchemaDtoConverter;
import by.bntu.constructor.web.util.converter.format.impl.JSONSchemaDtoConverter;
import by.bntu.constructor.web.util.converter.format.impl.XMLSchemaDtoConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

@Configuration
public class ComponentConfiguration {


    @Bean(name = "formatConverters")
    public Map<Format, SchemaDtoConverter> formatConverters() {
        Map<Format, SchemaDtoConverter> formats = new EnumMap<>(Format.class);
        formats.put(Format.XML, new XMLSchemaDtoConverter());
        formats.put(Format.JSON, new JSONSchemaDtoConverter());
        return formats;
    }
}
