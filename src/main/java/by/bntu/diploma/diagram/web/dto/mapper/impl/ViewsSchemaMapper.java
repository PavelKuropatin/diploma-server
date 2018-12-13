package by.bntu.diploma.diagram.web.dto.mapper.impl;

import by.bntu.diploma.diagram.entity.Diagram;
import by.bntu.diploma.diagram.web.dto.ViewSchemaDTO;
import by.bntu.diploma.diagram.web.dto.mapper.Mapper;
import org.mapstruct.Mapping;


@org.mapstruct.Mapper(componentModel = "spring")
public abstract class ViewsSchemaMapper implements Mapper<Diagram, ViewSchemaDTO> {

    @Mapping(target = "states", ignore = true)
    @Override
    public abstract Diagram fromDTO(ViewSchemaDTO diagramDTO);

}