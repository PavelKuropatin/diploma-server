package by.bntu.constructor.web.util.converter.format.impl;

import by.bntu.constructor.web.dto.SchemaDTO;
import by.bntu.constructor.web.util.converter.format.SchemaDtoConverter;
import by.bntu.constructor.web.util.exception.FormatException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

public class XMLSchemaDtoConverter implements SchemaDtoConverter {

    private ObjectMapper mapper = new XmlMapper();

    @Override
    public String to(SchemaDTO schemaDTO) {

        String data;
        try {
            data = mapper.writeValueAsString(schemaDTO);
        } catch (JsonProcessingException e) {
            throw new FormatException(e);
        }
        return data;
    }

    @Override
    public SchemaDTO from(String data) {

        SchemaDTO schemaDTO;
        try {
            schemaDTO = mapper.readValue(data, SchemaDTO.class);

        } catch (IOException e) {
            throw new FormatException(e);
        }
        return schemaDTO;
    }
}
