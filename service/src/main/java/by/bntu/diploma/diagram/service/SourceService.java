package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.domain.Source;
import by.bntu.diploma.diagram.domain.constraint.util.ValidationMessage;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface SourceService {

    Source saveSource(@Valid Source source);

    List<Source> saveAllSources(List<@Valid Source> sources);

    Source newSource();

    Source findBySourceUUID(
            @NotNull(message = ValidationMessage.Source.UUID_NULL)
            @Min(value = 1, message = ValidationMessage.Source.UUID_MIN) Long sourceUUID
    );
}