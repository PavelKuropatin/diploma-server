package by.bntu.diploma.diagram.web.dto.mapper.impl;

import by.bntu.diploma.diagram.domain.Target;
import by.bntu.diploma.diagram.web.dto.TargetDTO;
import by.bntu.diploma.diagram.web.dto.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class TargetMapper implements Mapper<Target, TargetDTO> {

}
