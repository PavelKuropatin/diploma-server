package by.bntu.diagram.web.dto.mapper.impl;

import by.bntu.diagram.domain.Style;
import by.bntu.diagram.web.dto.StyleDTO;
import by.bntu.diagram.web.dto.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class StyleMapper implements Mapper<Style, StyleDTO> {

}
