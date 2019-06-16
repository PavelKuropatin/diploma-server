package by.bntu.diagram.web.dto.mapper.impl;

import by.bntu.diagram.domain.Target;
import by.bntu.diagram.web.dto.TargetDTO;
import by.bntu.diagram.web.dto.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class TargetMapper implements Mapper<Target, TargetDTO> {

}
