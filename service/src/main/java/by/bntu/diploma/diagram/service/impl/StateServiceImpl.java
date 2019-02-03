package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.domain.*;
import by.bntu.diploma.diagram.repository.StateRepository;
import by.bntu.diploma.diagram.service.SourceService;
import by.bntu.diploma.diagram.service.StateService;
import by.bntu.diploma.diagram.service.StyleService;
import by.bntu.diploma.diagram.service.TargetService;
import by.bntu.diploma.diagram.service.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StateServiceImpl implements StateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateServiceImpl.class);

    private StateRepository stateRepo;
    private TargetService targetService;
    private SourceService sourceService;
    private StyleService styleService;


    @Override
    public State findByStateUUID(Long stateUUID) {
        if (stateUUID == null || stateUUID < 1) {
            throw new IllegalArgumentException("State UUID is null or less then 1. Got " + stateUUID);
        }
        Optional<State> stateOptional = this.stateRepo.findById(stateUUID);
        return stateOptional.orElse(null);
    }

    @Override
    @Transactional
    public List<State> saveAllStates(List<State> states) {
        return states.stream().map(this::saveState).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public State saveState(State state) {
        state.setStyle(this.styleService.saveStyle(state.getStyle()));
        state.setTargets(this.targetService.saveAllTargets(state.getTargets()));
        state.setSources(this.sourceService.saveAllSources(state.getSources()));
        return this.stateRepo.save(state);
    }

    @Override
    @Transactional
    public State newState() {
        State state;
        state = State.builder()
                .color("#CC1A55")
                .name("New State")
                .style(Style.builder()
                        .sourceAnchorStyle("RightMiddle")
                        .targetAnchorStyle("LeftMiddle")
                        .sourceStyle("endpoint-style-right")
                        .targetStyle("endpoint-style-left")
                        .build())
                .positionX(10)
                .positionY(10)
                .template("action")
                .build();
        state = this.saveState(state);
        this.newSource(state.getUuid());
        this.newTarget(state.getUuid());
        return state;
    }

    @Override
    @Transactional
    public Source newSource(Long stateUUID) {
        State state = this.findByStateUUID(stateUUID);
        if (state == null) {
            throw new NotFoundException("State[" + stateUUID + "] not found.");
        }
        Source source = this.sourceService.newSource();
        state.getSources().add(source);
        this.saveState(state);
        return source;
    }

    @Override
    @Transactional
    public Target newTarget(Long stateUUID) {
        State state = this.findByStateUUID(stateUUID);
        if (state == null) {
            throw new NotFoundException("State[" + stateUUID + "] not found.");
        }
        Target target = this.targetService.newTarget();
        state.getTargets().add(target);
        this.saveState(state);
        return target;
    }

    @Override
    @Transactional
    public void deleteSource(Long stateUUID, Long sourceUUID) {
        State state = this.findByStateUUID(stateUUID);
        Source source = this.sourceService.findBySourceUUID(sourceUUID);
        if (state == null) {
            throw new NotFoundException("State[" + stateUUID + "] not found.");
        }
        if (source == null) {
            throw new NotFoundException("Source[" + sourceUUID + "] not found.");
        }
        if (!state.getSources().contains(source)) {
            LOGGER.info("State[" + stateUUID + "] not contain Source[" + sourceUUID + "]. Deleting useless.");
        }
        state.getSources().removeIf(s -> s.equals(source));
        this.saveState(state);
    }

    @Override
    @Transactional
    public void deleteTarget(Long stateUUID, Long targetUUID) {
        State state = this.findByStateUUID(stateUUID);
        Target target = this.targetService.findByTargetUUID(targetUUID);
        if (state == null) {
            throw new NotFoundException("State[" + stateUUID + "] not found.");
        }
        if (target == null) {
            throw new NotFoundException("Target[" + targetUUID + "] not found.");
        }
        if (!state.getTargets().contains(target)) {
            LOGGER.info("State[" + stateUUID + "] not contain Target[" + targetUUID + "]. Deleting useless.");
        }
        state.getTargets().removeIf(t -> t.equals(target));
        this.saveState(state);
    }

    @Override
    public State putContainerValue(Long stateUUID, ContainerType type, String param, Double value) {
        State state = this.findByStateUUID(stateUUID);
        if (!ObjectUtils.allNotNull(type, param, value)) {
            throw new IllegalArgumentException("Type[" + type + "] param[" + value + "] or value[" + value + "] is null.");
        }
        Map<String, Double> container = type == ContainerType.INPUT ? state.getInputContainer() : state.getOutputContainer();
        container.put(param, value);
        state = this.saveState(state);
        return state;
    }
}
