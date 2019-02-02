package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.entity.Source;

import java.util.List;

public interface SourceService {

    Source saveSource(Source source);

    List<Source> saveAllSources(List<Source> sources);

    Source newSource();

    Source findBySourceUUID(Long sourceUUID);
}
