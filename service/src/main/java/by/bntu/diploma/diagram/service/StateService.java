package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.domain.Source;
import by.bntu.diploma.diagram.domain.State;
import by.bntu.diploma.diagram.domain.Target;
import by.bntu.diploma.diagram.domain.Variable;
import by.bntu.diploma.diagram.domain.constraint.util.ValidationMessage;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface StateService {

    State saveState(@Valid State state);

    List<State> saveAllStates(List<@Valid State> states);

    List<State> saveExternalStates(List<@Valid State> states);


    State findByStateUuid(
            @NotNull(message = ValidationMessage.State.UUID_NULL) String stateUuid
    );

    State newState();

    Source newSource(
            @NotNull(message = ValidationMessage.State.UUID_NULL) String stateUuid
    );

    Target newTarget(
            @NotNull(message = ValidationMessage.State.UUID_NULL) String stateUuid
    );

    void deleteSource(
            @NotNull(message = ValidationMessage.State.UUID_NULL) String stateUuid,
            @NotNull(message = ValidationMessage.Source.UUID_NULL) String sourceUuid
    );

    void deleteTarget(
            @NotNull(message = ValidationMessage.State.UUID_NULL) String stateUuid,
            @NotNull(message = ValidationMessage.Target.UUID_NULL) String targetUuid
    );

    State putVariable(
            @NotNull(message = ValidationMessage.State.UUID_NULL) String stateUuid,
            @NotNull(message = ValidationMessage.Variable.TYPE_NULL) Variable.Type type,
            @Valid Variable variable
    );


    State deleteVariable(
            @NotNull(message = ValidationMessage.State.UUID_NULL) String stateUuid,
            @NotNull(message = ValidationMessage.Variable.TYPE_NULL) Variable.Type type,
            @NotBlank String param
    );

}
