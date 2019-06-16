package by.bntu.diagram.web.util.converter.format.impl;

import by.bntu.diagram.web.dto.DiagramDTO;
import by.bntu.diagram.web.util.converter.format.DiagramDtoConverter;
import by.bntu.diagram.web.util.exception.FormatException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

public class XMLDiagramDtoConverter implements DiagramDtoConverter {

    private ObjectMapper mapper = new XmlMapper();

    @Override
    public String to(DiagramDTO diagramDTO) {

        String data;
        try {
            data = mapper.writeValueAsString(diagramDTO);
        } catch (JsonProcessingException e) {
            throw new FormatException(e);
        }
        return data;
    }

    @Override
    public DiagramDTO from(String data) {

        DiagramDTO diagramDTO;
        try {
            diagramDTO = mapper.readValue(data, DiagramDTO.class);

        } catch (IOException e) {
            throw new FormatException(e);
        }
        return diagramDTO;
    }
}
