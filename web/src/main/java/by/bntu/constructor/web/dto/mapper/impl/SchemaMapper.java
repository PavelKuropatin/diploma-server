package by.bntu.constructor.web.dto.mapper.impl;

import by.bntu.constructor.domain.Schema;
import by.bntu.constructor.web.dto.SchemaDTO;
import by.bntu.constructor.web.dto.mapper.Mapper;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;

import java.util.Collections;

@org.mapstruct.Mapper(componentModel = "spring", uses = BlockMapper.class)
public abstract class SchemaMapper implements Mapper<Schema, SchemaDTO> {

    @BeforeMapping
    void setDefaultsToDTO(SchemaDTO schemaDTO, @MappingTarget Schema schema) {
        if (schemaDTO.getBlocks() == null) {
            schemaDTO.setBlocks(Collections.emptyList());
        }
    }

    @BeforeMapping
    void setDefaultsToEntity(Schema schema, @MappingTarget SchemaDTO schemaDto) {
        if (schema.getBlocks() == null) {
            schema.setBlocks(Collections.emptyList());
        }
    }
}
