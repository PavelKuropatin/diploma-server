package by.bntu.constructor.web.util.converter;

import by.bntu.constructor.domain.Variable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VarTypeConverter implements Converter<String, Variable.Type> {

    @Override
    public Variable.Type convert(String type) {
        if (StringUtils.isBlank(type)) {
            return null;
        }
        type = type.toUpperCase();
        return Variable.Type.valueOf(type);
    }
}

