package by.bntu.diagram.web.dto.mapper.impl;

import by.bntu.diagram.domain.Diagram;
import by.bntu.diagram.web.dto.DiagramDTO;
import by.bntu.diagram.web.dto.mapper.Mapper;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;

import java.util.Collections;

@org.mapstruct.Mapper(componentModel = "spring", uses = StateMapper.class)
public abstract class DiagramMapper implements Mapper<Diagram, DiagramDTO> {

    @BeforeMapping
    void setDefaultsToDTO(DiagramDTO diagramDTO, @MappingTarget Diagram diagram) {
        if (diagramDTO.getStates() == null) {
            diagramDTO.setStates(Collections.emptyList());
        }
    }

    @BeforeMapping
    void setDefaultsToEntity(Diagram diagram, @MappingTarget DiagramDTO diagramDto) {
        if (diagram.getStates() == null) {
            diagram.setStates(Collections.emptyList());
        }
    }
}
