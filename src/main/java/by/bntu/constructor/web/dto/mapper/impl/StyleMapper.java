package by.bntu.constructor.web.dto.mapper.impl;

import by.bntu.constructor.domain.Style;
import by.bntu.constructor.web.dto.StyleDTO;
import by.bntu.constructor.web.dto.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class StyleMapper implements Mapper<Style, StyleDTO> {

}
