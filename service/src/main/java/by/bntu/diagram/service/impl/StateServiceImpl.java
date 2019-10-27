package by.bntu.diagram.service.impl;

import by.bntu.diagram.domain.*;
import by.bntu.diagram.repository.StateRepository;
import by.bntu.diagram.service.SourceService;
import by.bntu.diagram.service.StateService;
import by.bntu.diagram.service.StyleService;
import by.bntu.diagram.service.TargetService;
import by.bntu.diagram.service.exception.NotFoundException;
import by.bntu.diagram.service.utils.DomainUtils;
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
public class StateServiceImpl implements StateService {

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

        DomainUtils.dropDuplicateRefs(states);

        List<Style> styles = DomainUtils.extractStyleFromStates(states);
        List<Target> targets = DomainUtils.extractTargetsFromStates(states);
        List<Source> sources = DomainUtils.extractSourcesFromStates(states);

        styles.forEach(style -> style.setUuid(null));
        targets.forEach(target -> target.setUuid(null));
        sources.forEach(source -> source.setUuid(null));

        styleService.saveAllStyles(styles);
        targetService.saveAllTargets(targets);
        sourceService.saveAllSources(sources);

        return stateRepository.saveAll(states);
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
                .positionX(10.0)
                .positionY(10.0)
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
            throw new NotFoundException("State[" + stateUuid + "] not contain Source[" + sourceUuid + "]. Deleting useless.");
        }

        List<State> states = stateRepository.findBySourceReference(sourceUuid);
        DomainUtils.dropSources(states, state.getSources());

        states.forEach(s -> {
            s.getConnections().removeIf(c -> c.getSource().getUuid().equals(sourceUuid));
            s.getSources().removeIf(ss -> ss.getUuid().equals(sourceUuid));
        });
        state.getSources().removeIf(ss -> ss.getUuid().equals(sourceUuid));

        saveAllStates(states);
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
            throw new IllegalArgumentException("State[" + stateUuid + "] not contain Target[" + targetUuid + "]. Deleting useless.");
        }

        List<State> states = stateRepository.findByTargetReference(targetUuid);
        DomainUtils.dropTargets(states, state.getTargets());
        state.getTargets().removeIf(t -> t.getUuid().equals(target.getUuid()));

        saveAllStates(states);
    }

    @Override
    @Transactional
    public State putVariable(String stateUuid, Variable variable) {
        State state = findByStateUuid(stateUuid);
        if (state == null) {
            throw new NotFoundException(State.class, stateUuid);
        }

        List<Variable> variables;
        Variable.Type type = variable.getType();
        if (type == Variable.Type.INPUT) {
            variable.setFunction(null);
            variables = state.getInputContainer();
        } else {
            variables = state.getOutputContainer();
        }

        Optional<Variable> optional = variables.stream()
                .filter(v -> v.getParam().equals(variable.getParam()))
                .findFirst();
        if (optional.isPresent()) {
            throw new IllegalArgumentException("Already exist");
        }

        variables.add(variable);
        state = saveState(state);
        return state;
    }

    @Override
    @Transactional
    public State deleteVariable(String stateUuid, Variable variable) {
        State state = findByStateUuid(stateUuid);
        if (state == null) {
            throw new NotFoundException(State.class, stateUuid);
        }

        List<Variable> variables;
        if (variable.getType() == Variable.Type.INPUT) {
            variables = state.getInputContainer();
        } else {
            variables = state.getOutputContainer();
        }

        Optional<Variable> optional = variables.stream()
                .filter(v -> v.getParam().equals(variable.getParam()))
                .findFirst();
        if (!optional.isPresent()) {
            throw new IllegalArgumentException("Do not exist");
        }

        variables.removeIf(v -> v.getParam().equals(variable.getParam()));
        state = saveState(state);
        return state;
    }

    @Override
    @Transactional
    public State addConnection(String stateUuid, Connection connection) {
        State state = findByStateUuid(stateUuid);
        if (state == null) {
            throw new NotFoundException(State.class, stateUuid);
        }
        Source source = sourceService.findBySourceUuid(connection.getSource().getUuid());
        if (source == null) {
            throw new NotFoundException(Source.class, connection.getSource().getUuid());
        }
        Target target = targetService.findByTargetUuid(connection.getTarget().getUuid());
        if (target == null) {
            throw new NotFoundException(Target.class, connection.getTarget().getUuid());
        }

        connection.setSource(source);
        connection.setTarget(target);

        state.getConnections().add(connection);
        state = saveState(state);
        return state;
    }

    @Override
    @Transactional
    public State deleteConnection(String stateUuid, Connection connection) {
        State state = findByStateUuid(stateUuid);
        if (state == null) {
            throw new NotFoundException(State.class, stateUuid);
        }
        Source source = sourceService.findBySourceUuid(connection.getSource().getUuid());
        if (source == null) {
            throw new NotFoundException(Source.class, connection.getSource().getUuid());
        }
        Target target = targetService.findByTargetUuid(connection.getTarget().getUuid());
        if (target == null) {
            throw new NotFoundException(Target.class, connection.getTarget().getUuid());
        }
        if (!state.getConnections().contains(connection)) {
            throw new IllegalArgumentException("Do not exist");
        }
        state.getConnections().remove(connection);
        state = saveState(state);
        return state;
    }
}
