package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.domain.Source;
import by.bntu.diploma.diagram.repository.SourceRepository;
import by.bntu.diploma.diagram.service.SourceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SourceServiceImpl implements SourceService {

    private SourceRepository sourceRepository;

    @Override
    @Transactional
    public Source saveSource(Source source) {
        return sourceRepository.save(source);
    }

    @Override
    @Transactional
    public List<Source> saveAllSources(List<Source> sources) {
        sources.stream()
                .filter(source -> source.getUuid() != null)
                .filter(source -> !sourceRepository.existsById(source.getUuid()))
                .forEach(source -> source.setUuid(null));
        return sourceRepository.saveAll(sources);
    }

    @Override
    @Transactional
    public Source newSource() {
        Source source = Source.builder().build();
        return saveSource(source);
    }

    @Override
    public Source findBySourceUuid(String sourceUuid) {
        Optional<Source> sourceEndpointOptional = sourceRepository.findById(sourceUuid);
        return sourceEndpointOptional.orElse(null);
    }
}
