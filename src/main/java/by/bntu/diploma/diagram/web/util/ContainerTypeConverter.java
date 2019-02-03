package by.bntu.diploma.diagram.web.util;

import by.bntu.diploma.diagram.entity.State;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ContainerTypeConverter implements Converter<String, State.ContainerType> {

    @Override
    public State.ContainerType convert(String type) {
        if (StringUtils.isBlank(type)) {
            return null;
        }
        type = type.toUpperCase();
        return State.ContainerType.valueOf(type);
    }
}
