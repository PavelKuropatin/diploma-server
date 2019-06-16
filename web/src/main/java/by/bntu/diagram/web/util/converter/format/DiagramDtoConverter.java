package by.bntu.diagram.web.util.converter.format;

import by.bntu.diagram.web.dto.DiagramDTO;

public interface DiagramDtoConverter {


    String to(DiagramDTO diagramDTO);

    DiagramDTO from(String data);

}
