package by.bntu.constructor.web.util.converter;

import by.bntu.constructor.web.util.Format;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FormatTypeConverter implements Converter<String, Format> {

    @Override
    public Format convert(String format) {
        if (StringUtils.isBlank(format)) {
            return null;
        }
        format = format.toUpperCase();
        return Format.valueOf(format);
    }
}

