package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.entity.Source;
import by.bntu.diploma.diagram.entity.State;
import by.bntu.diploma.diagram.entity.Target;

import java.util.List;

public interface StateService {

    List<State> saveAllStates(List<State> states);

    State saveState(State state);

    State findByStateUUID(Long stateUUID);

    State newState();

    Source newSource(Long stateUUID);

    Target newTarget(Long stateUUID);

    void deleteSource(Long stateUUID, Long sourceUUID);

    void deleteTarget(Long stateUUID, Long targetUUID);
}
