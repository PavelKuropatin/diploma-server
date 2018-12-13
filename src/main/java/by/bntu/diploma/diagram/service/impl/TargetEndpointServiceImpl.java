package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.entity.TargetEndpoint;
import by.bntu.diploma.diagram.repository.TargetEndpointRepository;
import by.bntu.diploma.diagram.service.TargetEndpointService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TargetEndpointServiceImpl implements TargetEndpointService {

    private TargetEndpointRepository targetEndpointRepo;

    @Override
    @Transactional
    public TargetEndpoint save(@Valid TargetEndpoint targetEndpoint) {
        return this.targetEndpointRepo.save(targetEndpoint);
    }

    @Override
    @Transactional
    public List<TargetEndpoint> saveAll(List<TargetEndpoint> targetEndpoints) {
        return targetEndpoints.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TargetEndpoint newTargetEndpoint() {
        TargetEndpoint targetEndpoint = TargetEndpoint.builder().build();
        return this.save(targetEndpoint);
    }
}
