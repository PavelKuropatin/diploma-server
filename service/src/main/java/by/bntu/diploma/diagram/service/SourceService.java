package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.domain.Source;

import javax.validation.Valid;
import java.util.List;

public interface SourceService {

    Source saveSource(@Valid Source source);

    List<Source> saveAllSources(List<@Valid Source> sources);

    Source newSource();

    Source findBySourceUUID(Long sourceUUID);
}
