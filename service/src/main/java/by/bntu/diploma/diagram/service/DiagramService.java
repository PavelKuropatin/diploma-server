package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.domain.Diagram;
import by.bntu.diploma.diagram.domain.State;

import javax.validation.Valid;
import java.util.List;

public interface DiagramService {

    Diagram findDiagramByUUID(Long diagramUUID);

    Diagram saveDiagram(@Valid Diagram diagram);

    Diagram updateDiagram(@Valid Diagram diagram);

    void deleteDiagramByUUID(Long diagramUUID);

    List<Diagram> findAllDiagrams();

    Diagram newDiagram();

    State newState(Long diagramUUID);

    void deleteState(Long diagramUUID, Long stateUUID);
}
