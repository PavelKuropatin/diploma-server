package by.bntu.constructor.web.dto.mapper.impl;

import by.bntu.constructor.domain.Schema;
import by.bntu.constructor.web.dto.ViewSchemaDTO;
import by.bntu.constructor.web.dto.mapper.Mapper;
import org.mapstruct.Mapping;


@org.mapstruct.Mapper(componentModel = "spring")
public abstract class ViewSchemaMapper implements Mapper<Schema, ViewSchemaDTO> {

    @Mapping(target = "blocks", ignore = true)
    @Override
    public abstract Schema fromDTO(ViewSchemaDTO viewSchemaDTO);

}