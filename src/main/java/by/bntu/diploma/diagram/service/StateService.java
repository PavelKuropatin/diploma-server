package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.entity.SourceEndpoint;
import by.bntu.diploma.diagram.entity.State;
import by.bntu.diploma.diagram.entity.TargetEndpoint;

import java.util.List;

public interface StateService {

    List<State> saveAll(List<State> states);

    State saveState(State state);

    State findByStateUUID(Long stateUUID);

    State newState();

    SourceEndpoint newStateSourceEndpoint(Long stateUUID);

    TargetEndpoint newStateTargetEndpoint(Long stateUUID);
}
