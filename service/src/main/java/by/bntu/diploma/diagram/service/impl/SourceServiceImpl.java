package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.domain.Connection;
import by.bntu.diploma.diagram.domain.Source;
import by.bntu.diploma.diagram.repository.SourceRepository;
import by.bntu.diploma.diagram.service.ConnectionService;
import by.bntu.diploma.diagram.service.SourceService;
import by.bntu.diploma.diagram.service.utils.DomainUtils;
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
    private ConnectionService connectionService;

    @Override
    @Transactional
    public Source saveSource(Source source) {
        connectionService.saveAllConnections(source.getConnections());
        return sourceRepository.save(source);
    }

    @Override
    @Transactional
    public List<Source> saveAllSources(List<Source> sources) {
        List<Connection> connections = DomainUtils.extractConnectionsFromSources(sources);
        connectionService.saveAllConnections(connections);
        return sourceRepository.saveAll(sources);
    }

    @Override
    @Transactional
    public Source newSource() {
        Source source = Source.builder().build();
        return saveSource(source);
    }

    @Override
    public Source findBySourceUUID(Long sourceUUID) {
        Optional<Source> sourceEndpointOptional = sourceRepository.findById(sourceUUID);
        return sourceEndpointOptional.orElse(null);
    }
}
