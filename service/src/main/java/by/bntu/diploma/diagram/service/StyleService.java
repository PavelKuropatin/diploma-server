package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.domain.Style;

import javax.validation.Valid;

public interface StyleService {

    Style saveStyle(@Valid Style style);
}
