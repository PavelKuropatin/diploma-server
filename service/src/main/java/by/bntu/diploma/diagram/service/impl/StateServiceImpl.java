package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.domain.*;
import by.bntu.diploma.diagram.repository.StateRepository;
import by.bntu.diploma.diagram.service.SourceService;
import by.bntu.diploma.diagram.service.StateService;
import by.bntu.diploma.diagram.service.StyleService;
import by.bntu.diploma.diagram.service.TargetService;
import by.bntu.diploma.diagram.service.exception.NotFoundException;
import by.bntu.diploma.diagram.service.utils.DomainUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Validated
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StateServiceImpl implements StateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateServiceImpl.class);

    private StateRepository stateRepository;
    private TargetService targetService;
    private SourceService sourceService;
    private StyleService styleService;


    @Override
    public State findByStateUUID(Long stateUUID) {
        Optional<State> stateOptional = stateRepository.findById(stateUUID);
        return stateOptional.orElse(null);
    }

    @Override
    @Transactional
    public List<State> saveAllStates(List<State> states) {
        List<Style> styles = DomainUtils.extractStyleFromStates(states);
        List<Target> targets = DomainUtils.extractTargetsFromStates(states);
        List<Source> sources = DomainUtils.extractSourcesFromStates(states);
        styleService.saveAllStyles(styles);
        targetService.saveAllTargets(targets);
        sourceService.saveAllSources(sources);
        return stateRepository.saveAll(states);
    }

    @Override
    @Transactional
    public List<State> saveExternalStates(List<State> states) {
        states.forEach(state -> state.setUuid(null));
        List<Style> styles = DomainUtils.extractStyleFromStates(states);
        styles.forEach(style -> style.setUuid(null));

        List<Target> targets = DomainUtils.extractTargetsFromStates(states);

        DomainUtils.dropDuplicateTargets(states, targets);

        targets.forEach(target -> target.setUuid(null));

        List<Source> sources = DomainUtils.extractSourcesFromStates(states);
        sources.forEach(source -> source.setUuid(null));


        return saveAllStates(states);
    }


    @Override
    @Transactional
    public State saveState(State state) {
        styleService.saveStyle(state.getStyle());
        targetService.saveAllTargets(state.getTargets());
        sourceService.saveAllSources(state.getSources());
        return stateRepository.save(state);
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
        state = saveState(state);
        newSource(state.getUuid());
        newTarget(state.getUuid());
        return state;
    }

    @Override
    @Transactional
    public Source newSource(Long stateUUID) {
        State state = findByStateUUID(stateUUID);
        if (state == null) {
            throw new NotFoundException("State[" + stateUUID + "] not found.");
        }
        Source source = sourceService.newSource();
        state.getSources().add(source);
        saveState(state);
        return source;
    }

    @Override
    @Transactional
    public Target newTarget(Long stateUUID) {
        State state = findByStateUUID(stateUUID);
        if (state == null) {
            throw new NotFoundException("State[" + stateUUID + "] not found.");
        }
        Target target = targetService.newTarget();
        state.getTargets().add(target);
        saveState(state);
        return target;
    }

    @Override
    @Transactional
    public void deleteSource(Long stateUUID, Long sourceUUID) {
        State state = findByStateUUID(stateUUID);
        Source source = sourceService.findBySourceUUID(sourceUUID);
        if (state == null) {
            throw new NotFoundException("State[" + stateUUID + "] not found.");
        }
        if (source == null) {
            throw new NotFoundException("Source[" + sourceUUID + "] not found.");
        }
        if (!state.getSources().contains(source)) {
            LOGGER.info("State[" + stateUUID + "] not contain Source[" + sourceUUID + "]. Deleting useless.");
        } else {
            state.getSources().remove(source);
        }
        saveState(state);
    }

    @Override
    @Transactional
    public void deleteTarget(Long stateUUID, Long targetUUID) {
        State state = findByStateUUID(stateUUID);
        Target target = targetService.findByTargetUUID(targetUUID);
        if (state == null) {
            throw new NotFoundException("State[" + stateUUID + "] not found.");
        }
        if (target == null) {
            throw new NotFoundException("Target[" + targetUUID + "] not found.");
        }
        if (!state.getTargets().contains(target)) {
            LOGGER.info("State[" + stateUUID + "] not contain Target[" + targetUUID + "]. Deleting useless.");
        } else {
            state.getTargets().remove(target);
        }
        saveState(state);
    }

    @Override
    @Transactional
    public State putContainerValue(Long stateUUID, ContainerType type, String param, Double value) {
        State state = findByStateUUID(stateUUID);
        Map<String, Double> container = type == ContainerType.INPUT ? state.getInputContainer() : state.getOutputContainer();
        container.put(param, value);
        state = saveState(state);
        return state;
    }
}
