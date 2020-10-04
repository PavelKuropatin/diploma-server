package by.bntu.constructor.web.dto.mapper.impl;

import by.bntu.constructor.domain.Action;
import by.bntu.constructor.domain.Input;
import by.bntu.constructor.web.dto.ActionDTO;
import by.bntu.constructor.web.dto.InputDTO;
import by.bntu.constructor.web.dto.mapper.Mapper;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;

import java.util.Collections;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class ActionMapper implements Mapper<Action, ActionDTO> {
}
