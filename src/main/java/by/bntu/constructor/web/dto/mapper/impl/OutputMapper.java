package by.bntu.constructor.web.dto.mapper.impl;

import by.bntu.constructor.domain.Output;
import by.bntu.constructor.web.dto.mapper.Mapper;
import by.bntu.constructor.web.dto.OutputDTO;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class OutputMapper implements Mapper<Output, OutputDTO> {

}
