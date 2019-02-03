package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.domain.ContainerType;
import by.bntu.diploma.diagram.domain.Source;
import by.bntu.diploma.diagram.domain.State;
import by.bntu.diploma.diagram.domain.Target;

import javax.validation.Valid;
import java.util.List;

public interface StateService {

    List<State> saveAllStates(List<@Valid State> states);

    State saveState(@Valid State state);

    State findByStateUUID(Long stateUUID);

    State newState();

    Source newSource(Long stateUUID);

    Target newTarget(Long stateUUID);

    void deleteSource(Long stateUUID, Long sourceUUID);

    void deleteTarget(Long stateUUID, Long targetUUID);

    State putContainerValue(Long stateUUID, ContainerType type, String param, Double value);
}
