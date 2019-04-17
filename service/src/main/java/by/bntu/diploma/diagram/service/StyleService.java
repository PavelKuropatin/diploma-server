package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.domain.Style;

import javax.validation.Valid;
import java.util.List;

public interface StyleService {

    Style saveStyle(@Valid Style style);

    List<Style> saveAllStyles(List<@Valid Style> styles);
}
