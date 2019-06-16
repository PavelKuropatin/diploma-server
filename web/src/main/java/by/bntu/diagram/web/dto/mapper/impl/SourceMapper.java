package by.bntu.diagram.web.dto.mapper.impl;

import by.bntu.diagram.domain.Source;
import by.bntu.diagram.web.dto.SourceDTO;
import by.bntu.diagram.web.dto.mapper.Mapper;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;

import java.util.Collections;

@org.mapstruct.Mapper(componentModel = "spring", uses = {ConnectionMapper.class})
public abstract class SourceMapper implements Mapper<Source, SourceDTO> {

    @BeforeMapping
    void setDefaultsToDTO(SourceDTO sourceDTO, @MappingTarget Source source) {
        if (sourceDTO.getConnections() == null) {
            sourceDTO.setConnections(Collections.emptyList());
        }
    }
}
