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
    public State findByStateUuid(String stateUuid) {
        Optional<State> stateOptional = stateRepository.findById(stateUuid);
        return stateOptional.orElse(null);
    }

    @Override
    @Transactional
    public List<State> saveAllStates(List<State> states) {
        DomainUtils.dropDuplicateRefs(states);

        List<Style> styles = DomainUtils.extractStyleFromStates(states);
        List<Target> targets = DomainUtils.extractTargetsFromStates(states);
        List<Source> sources = DomainUtils.extractSourcesFromStates(states);

        styleService.saveAllStyles(styles);
        targetService.saveAllTargets(targets);
        sourceService.saveAllSources(sources);

        states.stream()
                .filter(state -> state.getUuid() != null)
                .filter(state -> !stateRepository.existsById(state.getUuid()))
                .forEach(state -> state.setUuid(null));
        return stateRepository.saveAll(states);
    }

    @Override
    @Transactional
    public List<State> saveExternalStates(List<State> states) {
        states.forEach(state -> state.setUuid(null));
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
                .positionX(.10)
                .positionY(.10)
                .template("action")
                .build();
        state = saveState(state);
        newSource(state.getUuid());
        newTarget(state.getUuid());
        return state;
    }

    @Override
    @Transactional
    public Source newSource(String stateUuid) {
        State state = findByStateUuid(stateUuid);
        if (state == null) {
            throw new NotFoundException(State.class, stateUuid);
        }
        Source source = sourceService.newSource();
        state.getSources().add(source);
        saveState(state);
        return source;
    }

    @Override
    @Transactional
    public Target newTarget(String stateUuid) {
        State state = findByStateUuid(stateUuid);
        if (state == null) {
            throw new NotFoundException(State.class, stateUuid);
        }
        Target target = targetService.newTarget();
        state.getTargets().add(target);
        saveState(state);
        return target;
    }

    @Override
    @Transactional
    public void deleteSource(String stateUuid, String sourceUuid) {
        State state = findByStateUuid(stateUuid);
        Source source = sourceService.findBySourceUuid(sourceUuid);
        if (state == null) {
            throw new NotFoundException(State.class, stateUuid);
        }
        if (source == null) {
            throw new NotFoundException(Source.class, sourceUuid);
        }
        if (!state.getSources().contains(source)) {
            LOGGER.info("State[" + stateUuid + "] not contain Source[" + sourceUuid + "]. Deleting useless.");
        } else {
            state.getSources().remove(source);
        }
        saveState(state);
    }

    @Override
    @Transactional
    public void deleteTarget(String stateUuid, String targetUuid) {
        State state = findByStateUuid(stateUuid);
        Target target = targetService.findByTargetUuid(targetUuid);
        if (state == null) {
            throw new NotFoundException(State.class, stateUuid);
        }
        if (target == null) {
            throw new NotFoundException(Target.class, targetUuid);
        }
        if (!state.getTargets().contains(target)) {
            LOGGER.info("State[" + stateUuid + "] not contain Target[" + targetUuid + "]. Deleting useless.");
        } else {
            state.getTargets().remove(target);
        }
        saveState(state);
    }

    @Override
    @Transactional
    public State putVariable(String stateUuid, Variable.Type type, Variable variable) {
        State state = findByStateUuid(stateUuid);
        if (state == null) {
            throw new NotFoundException(State.class, stateUuid);
        }
        List<Variable> variables = type == Variable.Type.INPUT ? state.getInputContainer() : state.getOutputContainer();
        Optional<Variable> optional = variables.stream()
                .filter(v -> v.getParam().equals(variable.getParam()))
                .findFirst();
        if (optional.isPresent()) {
            Variable v = optional.get();
            v.setValue(variable.getValue());
            v.setFunction(variable.getFunction());
        } else {
            variables.add(variable);
        }
        state = saveState(state);
        return state;
    }

    @Override
    @Transactional
    public State deleteVariable(String stateUuid, Variable.Type type, String param) {
        State state = findByStateUuid(stateUuid);
        if (state == null) {
            throw new NotFoundException(State.class, stateUuid);
        }
        List<Variable> variables = type == Variable.Type.INPUT ? state.getInputContainer() : state.getOutputContainer();
        variables.removeIf(variable -> variable.getParam().equals(param));
        state = saveState(state);
        return state;
    }
}
