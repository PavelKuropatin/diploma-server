package by.bntu.diploma.diagram.web.dto.mapper.impl;

import by.bntu.diploma.diagram.entity.Source;
import by.bntu.diploma.diagram.web.dto.SourceDTO;
import by.bntu.diploma.diagram.web.dto.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring", uses = {ConnectionMapper.class})
public abstract class SourceMapper implements Mapper<Source, SourceDTO> {

}
