package by.bntu.constructor.web.dto.mapper.impl;

import by.bntu.constructor.domain.Variable;
import by.bntu.constructor.web.dto.VariableDTO;
import by.bntu.constructor.web.dto.mapper.Mapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class VariableMapper implements Mapper<Variable, VariableDTO> {

    private static final List<String> types = Arrays.stream(Variable.Type.values())
            .map(Enum::name)
            .collect(Collectors.toList());

    @Mapping(target = "type", ignore = true)
    @Override
    public abstract VariableDTO toDTO(Variable variable);


    @Mapping(target = "type", ignore = true)
    @Override
    public abstract Variable fromDTO(VariableDTO variableDTO);


    @AfterMapping
    void convertVariableTypeForEntity(VariableDTO variableDTO, @MappingTarget Variable variable) {
        if (variableDTO.getType() == null) {
            return;
        }
        String type = variableDTO.getType().toUpperCase();
        if (!types.contains(type)) {
            return;
        }
        variable.setType(Variable.Type.valueOf(type));
    }

    @AfterMapping
    void convertVariableTypeForDTO(Variable variable, @MappingTarget VariableDTO variableDTO) {
        if (variable.getType() == null) {
            return;
        }
        String type = variable.getType().name();
        variableDTO.setType(type);
    }

}
