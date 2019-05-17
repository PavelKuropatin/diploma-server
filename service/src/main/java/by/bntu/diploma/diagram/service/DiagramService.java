package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.domain.Diagram;
import by.bntu.diploma.diagram.domain.State;
import by.bntu.diploma.diagram.domain.constraint.util.ValidationMessage;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface DiagramService {

    Diagram findDiagramByUuid(
            @NotNull(message = ValidationMessage.Diagram.UUID_NULL) String diagramUuid
    );

    Diagram saveDiagram(@Valid Diagram diagram);

    Diagram updateDiagram(@Valid Diagram diagram);

    void deleteDiagramByUuid(
            @NotNull(message = ValidationMessage.Diagram.UUID_NULL) String diagramUuid
    );

    List<Diagram> findAllDiagrams();

    Diagram newDiagram();

    State newState(
            @NotNull(message = ValidationMessage.Diagram.UUID_NULL) String diagramUuid
    );

    Diagram deleteState(
            @NotNull(message = ValidationMessage.Diagram.UUID_NULL) String diagramUuid,
            @NotNull(message = ValidationMessage.State.UUID_NULL) String stateUuid
    );

    Diagram saveExternalDiagram(@Valid Diagram diagram);

    Diagram findByStateUuid(
            @NotNull(message = ValidationMessage.State.UUID_NULL) String stateUuid
    );
}
