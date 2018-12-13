package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.entity.Diagram;
import by.bntu.diploma.diagram.entity.State;

import java.util.List;

public interface DiagramService {

    Diagram findDiagramByUUID(Long diagramUUID);

    Diagram save(Diagram diagram);

    Diagram update(Diagram diagram);

    void deleteDiagramByUUID(Long diagramUUID);

    List<Diagram> findAll();

    Diagram createNewDiagram();

    State newDiagramState(Long diagramUUID);
}
