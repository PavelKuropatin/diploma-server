package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.entity.SourceEndpoint;
import by.bntu.diploma.diagram.repository.SourceEndpointRepository;
import by.bntu.diploma.diagram.service.ConnectionService;
import by.bntu.diploma.diagram.service.SourceEndpointService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SourceEndpointServiceImpl implements SourceEndpointService {

    private SourceEndpointRepository sourceEndpointRepo;
    private ConnectionService connectionService;

    @Override
    @Transactional
    public SourceEndpoint save(@Valid SourceEndpoint sourceEndpoint) {
        sourceEndpoint.setConnections(this.connectionService.saveAll(sourceEndpoint.getConnections()));
        return this.sourceEndpointRepo.save(sourceEndpoint);
    }

    @Override
    @Transactional
    public List<SourceEndpoint> saveAll(List<SourceEndpoint> sourceEndpoints) {
        return sourceEndpoints.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SourceEndpoint newSourceEndpoint() {
        SourceEndpoint sourceEndpoint = SourceEndpoint.builder().build();
        return this.save(sourceEndpoint);
    }
}
