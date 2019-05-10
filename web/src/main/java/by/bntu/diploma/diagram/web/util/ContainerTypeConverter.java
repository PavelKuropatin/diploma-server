package by.bntu.diploma.diagram.web.util;

import by.bntu.diploma.diagram.domain.Variable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ContainerTypeConverter implements Converter<String, Variable.Type> {

    @Override
    public Variable.Type convert(String type) {
        if (StringUtils.isBlank(type)) {
            return null;
        }
        type = type.toUpperCase();
        return Variable.Type.valueOf(type);
    }
}

