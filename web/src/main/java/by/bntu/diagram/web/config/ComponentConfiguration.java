package by.bntu.diagram.web.config;

import by.bntu.diagram.web.util.Format;
import by.bntu.diagram.web.util.converter.format.DiagramDtoConverter;
import by.bntu.diagram.web.util.converter.format.impl.JSONDiagramDtoConverter;
import by.bntu.diagram.web.util.converter.format.impl.XMLDiagramDtoConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

@Configuration
public class ComponentConfiguration {


    @Bean(name = "formatConverters")
    public Map<Format, DiagramDtoConverter> formatConverters() {
        Map<Format, DiagramDtoConverter> formats = new EnumMap<>(Format.class);
        formats.put(Format.XML, new XMLDiagramDtoConverter());
        formats.put(Format.JSON, new JSONDiagramDtoConverter());
        return formats;
    }
}
