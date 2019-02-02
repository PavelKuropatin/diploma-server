package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.entity.Source;
import by.bntu.diploma.diagram.repository.SourceRepository;
import by.bntu.diploma.diagram.service.ConnectionService;
import by.bntu.diploma.diagram.service.SourceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SourceServiceImpl implements SourceService {

    private SourceRepository sourceRepo;
    private ConnectionService connectionService;

    @Override
    @Transactional
    public Source saveSource(@Valid Source source) {
        source.setConnections(this.connectionService.saveAllConnections(source.getConnections()));
        return this.sourceRepo.save(source);
    }

    @Override
    @Transactional
    public List<Source> saveAllSources(List<Source> sources) {
        return sources.stream().map(this::saveSource).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Source newSource() {
        Source source = Source.builder().build();
        return this.saveSource(source);
    }

    @Override
    public Source findBySourceUUID(Long sourceUUID) {
        if (sourceUUID == null || sourceUUID < 1) {
            throw new IllegalArgumentException("Source UUID is null or less then 1. Got" + sourceUUID);
        }
        Optional<Source> sourceEndpointOptional = this.sourceRepo.findById(sourceUUID);
        return sourceEndpointOptional.orElse(null);
    }
}
