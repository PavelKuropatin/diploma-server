package by.bntu.diagram.service;

import by.bntu.diagram.domain.Source;
import by.bntu.diagram.domain.constraint.util.ValidationMessage;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface SourceService {

    Source saveSource(@Valid Source source);

    List<Source> saveAllSources(List<@Valid Source> sources);

    Source newSource();

    Source findBySourceUuid(
            @NotNull(message = ValidationMessage.Source.UUID_NULL) String sourceUuid
    );
}