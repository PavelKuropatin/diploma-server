package by.bntu.constructor.service;

import by.bntu.constructor.domain.Style;

import javax.validation.Valid;
import java.util.List;

public interface StyleService {

    Style saveStyle(@Valid Style style);

    List<Style> saveAllStyles(List<@Valid Style> styles);
}
