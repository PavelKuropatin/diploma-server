package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.domain.ContainerType;
import by.bntu.diploma.diagram.domain.Source;
import by.bntu.diploma.diagram.domain.State;
import by.bntu.diploma.diagram.domain.Target;
import by.bntu.diploma.diagram.domain.constraint.util.ValidationMessage;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface StateService {

    List<State> saveAllStates(List<@Valid State> states);

    List<State> saveExternalStates(List<@Valid State> states);

    State saveState(@Valid State state);

    State findByStateUUID(
            @NotNull(message = ValidationMessage.State.UUID_NULL)
            @Min(value = 1, message = ValidationMessage.State.UUID_MIN) Long stateUUID
    );

    State newState();

    Source newSource(
            @NotNull(message = ValidationMessage.State.UUID_NULL)
            @Min(value = 1, message = ValidationMessage.State.UUID_MIN) Long stateUUID
    );

    Target newTarget(
            @NotNull(message = ValidationMessage.State.UUID_NULL)
            @Min(value = 1, message = ValidationMessage.State.UUID_MIN) Long stateUUID
    );

    void deleteSource(
            @NotNull(message = ValidationMessage.State.UUID_NULL)
            @Min(value = 1, message = ValidationMessage.State.UUID_MIN) Long stateUUID,
            @NotNull(message = ValidationMessage.Source.UUID_NULL)
            @Min(value = 1, message = ValidationMessage.State.UUID_MIN) Long sourceUUID
    );

    void deleteTarget(
            @NotNull(message = ValidationMessage.State.UUID_NULL)
            @Min(value = 1, message = ValidationMessage.State.UUID_MIN) Long stateUUID,
            @NotNull(message = ValidationMessage.Target.UUID_NULL)
            @Min(value = 1, message = ValidationMessage.State.UUID_MIN) Long targetUUID
    );

    State putContainerValue(
            @NotNull(message = ValidationMessage.State.UUID_NULL)
            @Min(value = 1, message = ValidationMessage.State.UUID_MIN) Long stateUUID,
            @NotNull(message = ValidationMessage.Container.TYPE_NULL) ContainerType type,
            @NotBlank(message = ValidationMessage.Container.KEY_BLANK) String param,
            @NotNull(message = ValidationMessage.Container.VALUE_NULL) Double value
    );

}
