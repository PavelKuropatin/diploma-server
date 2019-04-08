package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.domain.Diagram;
import by.bntu.diploma.diagram.domain.State;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface DiagramService {

    Diagram findDiagramByUUID(
            @NotNull(message = "{diagram.uuid.null}") @Min(value = 1, message = "{diagram.uuid.min}") Long diagramUUID
    );

    Diagram saveDiagram(@Valid Diagram diagram);

    Diagram updateDiagram(@Valid Diagram diagram);

    void deleteDiagramByUUID(
            @NotNull(message = "{diagram.uuid.null}") @Min(value = 1, message = "{diagram.uuid.min}") Long diagramUUID
    );

    List<Diagram> findAllDiagrams();

    Diagram newDiagram();

    State newState(
            @NotNull(message = "{diagram.uuid.null}") @Min(value = 1, message = "{diagram.uuid.min}") Long diagramUUID
    );

    void deleteState(
            @NotNull(message = "{diagram.uuid.null}") @Min(value = 1, message = "{diagram.uuid.min}") Long diagramUUID,
            @NotNull(message = "{state.uuid.null}") @Min(value = 1, message = "{state.uuid.min}") Long stateUUID
    );
}
