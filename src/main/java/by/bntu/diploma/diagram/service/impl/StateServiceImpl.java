package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.entity.EndpointStyle;
import by.bntu.diploma.diagram.entity.SourceEndpoint;
import by.bntu.diploma.diagram.entity.State;
import by.bntu.diploma.diagram.entity.TargetEndpoint;
import by.bntu.diploma.diagram.repository.StateRepository;
import by.bntu.diploma.diagram.service.EndpointStyleService;
import by.bntu.diploma.diagram.service.SourceEndpointService;
import by.bntu.diploma.diagram.service.StateService;
import by.bntu.diploma.diagram.service.TargetEndpointService;
import by.bntu.diploma.diagram.service.exception.StateNotFoundException;
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
public class StateServiceImpl implements StateService {

    private StateRepository stateRepo;
    private TargetEndpointService targetEndpointService;
    private SourceEndpointService sourceEndpointService;
    private EndpointStyleService endpointStyleService;


    @Override
    public State findByStateUUID(Long stateUUID) {
        if (stateUUID == null || stateUUID < 1) {
            throw new IllegalArgumentException("State UUID is null or less then 1");
        }
        Optional<State> stateOptional = this.stateRepo.findById(stateUUID);
        return stateOptional.orElse(null);
    }

    @Override
    @Transactional
    public List<State> saveAll(List<State> states) {
        return states.stream().map(this::saveState).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public State saveState(@Valid State state) {
        state.setEndpointStyle(this.endpointStyleService.save(state.getEndpointStyle()));
        state.setTargetEndpoints(this.targetEndpointService.saveAll(state.getTargetEndpoints()));
        state.setSourceEndpoints(this.sourceEndpointService.saveAll(state.getSourceEndpoints()));
        return this.stateRepo.save(state);
    }

    @Override
    @Transactional
    public State newState() {
        State state = State.builder()
                .color("#CC1A55")
                .name("New State")
                .endpointStyle(EndpointStyle.builder()
                        .sourceEndpointStyle("endpoint-style-right")
                        .targetEndpointStyle("endpoint-circle-style-left")
                        .build())
                .positionX(10)
                .positionY(10)
                .template("action")
                .build();
        return this.saveState(state);
    }

    @Override
    @Transactional
    public SourceEndpoint newStateSourceEndpoint(Long stateUUID) {
        State state = this.findByStateUUID(stateUUID);
        if (state == null) {
            throw new StateNotFoundException("State not found (uuid = " + stateUUID + ")");
        }
        SourceEndpoint sourceEndpoint = this.sourceEndpointService.newSourceEndpoint();
        state.getSourceEndpoints().add(sourceEndpoint);
        this.saveState(state);
        return sourceEndpoint;
    }

    @Override
    @Transactional
    public TargetEndpoint newStateTargetEndpoint(Long stateUUID) {
        State state = this.findByStateUUID(stateUUID);
        if (state == null) {
            throw new StateNotFoundException("State not found (uuid = " + stateUUID + ")");
        }
        TargetEndpoint targetEndpoint = this.targetEndpointService.newTargetEndpoint();
        state.getTargetEndpoints().add(targetEndpoint);
        this.saveState(state);
        return targetEndpoint;
    }
}
