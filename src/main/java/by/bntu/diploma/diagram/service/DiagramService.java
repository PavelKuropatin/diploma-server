package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.entity.Diagram;
import by.bntu.diploma.diagram.entity.State;

import java.util.List;

public interface DiagramService {

    Diagram findDiagramByUUID(Long diagramUUID);

    Diagram saveDiagram(Diagram diagram);

    Diagram updateDiagram(Diagram diagram);

    void deleteDiagramByUUID(Long diagramUUID);

    List<Diagram> findAllDiagrams();

    Diagram newDiagram();

    State newState(Long diagramUUID);

    void deleteState(Long diagramUUID, Long stateUUID);
}
