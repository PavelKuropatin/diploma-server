package by.bntu.diploma.diagram.web.dto.mapper.impl;

import by.bntu.diploma.diagram.domain.Variable;
import by.bntu.diploma.diagram.web.dto.VariableDTO;
import by.bntu.diploma.diagram.web.dto.mapper.Mapper;
import org.mapstruct.Mapping;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class VariableMapper implements Mapper<Variable, VariableDTO> {

    @Mapping(target = "type", ignore = true)
    @Override
    public abstract VariableDTO toDTO(Variable variable);
}
