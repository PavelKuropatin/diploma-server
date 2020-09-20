package by.bntu.constructor.web.dto.mapper.impl;

import by.bntu.constructor.domain.Input;
import by.bntu.constructor.web.dto.InputDTO;
import by.bntu.constructor.web.dto.mapper.Mapper;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;

import java.util.Collections;

@org.mapstruct.Mapper(componentModel = "spring", uses = {ConnectionMapper.class})
public abstract class InputMapper implements Mapper<Input, InputDTO> {

    @BeforeMapping
    void setDefaultsToDTO(InputDTO inputDTO, @MappingTarget Input input) {
        if (inputDTO.getConnections() == null) {
            inputDTO.setConnections(Collections.emptyList());
        }
    }
}
