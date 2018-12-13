package by.bntu.diploma.diagram.web.dto.mapper.impl;

import by.bntu.diploma.diagram.entity.SourceEndpoint;
import by.bntu.diploma.diagram.web.dto.SourceEndpointDTO;
import by.bntu.diploma.diagram.web.dto.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring", uses = {ConnectionMapper.class})
public abstract class SourceEndpointMapper implements Mapper<SourceEndpoint, SourceEndpointDTO> {

}
