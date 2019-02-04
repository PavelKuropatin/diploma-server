package by.bntu.diploma.diagram.web.dto.mapper.impl;

import by.bntu.diploma.diagram.domain.Diagram;
import by.bntu.diploma.diagram.web.dto.DiagramDTO;
import by.bntu.diploma.diagram.web.dto.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring", uses = StateMapper.class)
public abstract class DiagramMapper implements Mapper<Diagram, DiagramDTO> {

}
