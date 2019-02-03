package by.bntu.diploma.diagram.web.dto.mapper.impl;

import by.bntu.diploma.diagram.domain.Style;
import by.bntu.diploma.diagram.web.dto.StyleDTO;
import by.bntu.diploma.diagram.web.dto.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class StyleMapper implements Mapper<Style, StyleDTO> {

}
