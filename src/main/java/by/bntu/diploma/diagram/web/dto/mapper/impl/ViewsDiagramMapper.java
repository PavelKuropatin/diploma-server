package by.bntu.diploma.diagram.web.dto.mapper.impl;

import by.bntu.diploma.diagram.entity.Diagram;
import by.bntu.diploma.diagram.web.dto.ViewDiagramDTO;
import by.bntu.diploma.diagram.web.dto.mapper.Mapper;
import org.mapstruct.Mapping;


@org.mapstruct.Mapper(componentModel = "spring")
public abstract class ViewsDiagramMapper implements Mapper<Diagram, ViewDiagramDTO> {

    @Mapping(target = "states", ignore = true)
    @Override
    public abstract Diagram fromDTO(ViewDiagramDTO diagramDTO);

}