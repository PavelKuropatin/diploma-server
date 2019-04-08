package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.domain.Source;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface SourceService {

    Source saveSource(@Valid Source source);

    List<Source> saveAllSources(List<@Valid Source> sources);

    Source newSource();

    Source findBySourceUUID(
            @NotNull(message = "{source.uuid.null}") @Min(value = 1, message = "{source.uuid.min}") Long sourceUUID
    );
}
