package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.domain.Diagram;
import by.bntu.diploma.diagram.domain.State;
import by.bntu.diploma.diagram.domain.constraint.util.ValidationMessage;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface DiagramService {

    Diagram findDiagramByUUID(
            @NotNull(message = ValidationMessage.Diagram.UUID_NULL)
            @Min(value = 1, message = ValidationMessage.Diagram.UUID_MIN) Long diagramUUID
    );

    Diagram saveDiagram(@Valid Diagram diagram);

    Diagram updateDiagram(@Valid Diagram diagram);

    void deleteDiagramByUUID(
            @NotNull(message = ValidationMessage.Diagram.UUID_NULL)
            @Min(value = 1, message = ValidationMessage.Diagram.UUID_MIN) Long diagramUUID
    );

    List<Diagram> findAllDiagrams();

    Diagram newDiagram();

    State newState(
            @NotNull(message = ValidationMessage.Diagram.UUID_NULL)
            @Min(value = 1, message = ValidationMessage.Diagram.UUID_MIN) Long diagramUUID
    );

    void deleteState(
            @NotNull(message = ValidationMessage.Diagram.UUID_NULL)
            @Min(value = 1, message = ValidationMessage.Diagram.UUID_MIN) Long diagramUUID,
            @NotNull(message = ValidationMessage.State.UUID_NULL)
            @Min(value = 1, message = ValidationMessage.State.UUID_MIN) Long stateUUID
    );

    Diagram saveExternalDiagram(@Valid Diagram diagram);

}
