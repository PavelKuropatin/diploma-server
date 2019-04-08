package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.domain.ContainerType;
import by.bntu.diploma.diagram.domain.Source;
import by.bntu.diploma.diagram.domain.State;
import by.bntu.diploma.diagram.domain.Target;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface StateService {

    List<State> saveAllStates(List<@Valid State> states);

    State saveState(@Valid State state);

    State findByStateUUID(
            @NotNull(message = "{state.uuid.null}") @Min(value = 1, message = "{state.uuid.min}") Long stateUUID
    );

    State newState();

    Source newSource(
            @NotNull(message = "{state.uuid.null}") @Min(value = 1, message = "{state.uuid.min}") Long stateUUID
    );

    Target newTarget(
            @NotNull(message = "{state.uuid.null}") @Min(value = 1, message = "{state.uuid.min}") Long stateUUID
    );

    void deleteSource(
            @NotNull(message = "{state.uuid.null}") @Min(value = 1, message = "{state.uuid.min}") Long stateUUID,
            @NotNull(message = "{source.uuid.null}") @Min(value = 1, message = "{source.uuid.min}") Long sourceUUID
    );

    void deleteTarget(
            @NotNull(message = "{state.uuid.null}") @Min(value = 1, message = "{state.uuid.min}") Long stateUUID,
            @NotNull(message = "{target.uuid.null}") @Min(value = 1, message = "{target.uuid.min}") Long targetUUID
    );

    State putContainerValue(
            @NotNull(message = "{state.uuid.null}") @Min(value = 1, message = "{state.uuid.min}") Long stateUUID,
            @NotNull(message = "{container.type.null}") ContainerType type,
            @NotNull(message = "{container.key.null}") @NotBlank(message = "{container.key.blank}") String param,
            @NotNull(message = "{container.value.null}") Double value
    );
}
