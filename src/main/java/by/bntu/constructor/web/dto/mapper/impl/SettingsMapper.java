package by.bntu.constructor.web.dto.mapper.impl;

import by.bntu.constructor.domain.Settings;
import by.bntu.constructor.web.dto.SettingsDTO;
import by.bntu.constructor.web.dto.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring", uses = {ActionMapper.class})
public abstract class SettingsMapper implements Mapper<Settings, SettingsDTO> {

}
