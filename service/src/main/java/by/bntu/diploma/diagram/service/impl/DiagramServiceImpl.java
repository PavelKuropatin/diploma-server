package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.domain.Diagram;
import by.bntu.diploma.diagram.domain.State;
import by.bntu.diploma.diagram.repository.DiagramRepository;
import by.bntu.diploma.diagram.service.DiagramService;
import by.bntu.diploma.diagram.service.StateService;
import by.bntu.diploma.diagram.service.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DiagramServiceImpl implements DiagramService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiagramServiceImpl.class);

    private DiagramRepository diagramRepository;
    private StateService stateService;

    @Override
    public Diagram findDiagramByUUID(Long diagramUUID) {
        return diagramRepository.findById(diagramUUID).orElse(null);
    }

    @Override
    @Transactional
    public Diagram saveDiagram(Diagram diagram) {
        stateService.saveAllStates(diagram.getStates());
        return diagramRepository.save(diagram);
    }

    @Transactional
    public Diagram updateDiagram(Diagram diagram) {
        Long diagramUUID = diagram.getUuid();
        if (!diagramRepository.existsById(diagramUUID)) {
            throw new NotFoundException(Diagram.class, diagramUUID);
        }
        return saveDiagram(diagram);
    }

    @Override
    @Transactional
    public void deleteDiagramByUUID(Long diagramUUID) {
        diagramRepository.deleteById(diagramUUID);
    }

    @Override
    public List<Diagram> findAllDiagrams() {
        return diagramRepository.findAll();
    }

    @Override
    @Transactional
    public Diagram newDiagram() {
        Diagram diagram = Diagram.builder()
                .name("Diagram name")
                .description("Diagram description")
                .build();
        return saveDiagram(diagram);
    }

    @Override
    @Transactional
    public State newState(Long diagramUUID) {
        Diagram diagram = findDiagramByUUID(diagramUUID);
        if (diagram == null) {
            throw new NotFoundException(Diagram.class, diagramUUID);
        }
        State newState = stateService.newState();
        diagram.getStates().add(newState);
        saveDiagram(diagram);
        return newState;
    }

    @Override
    @Transactional
    public void deleteState(Long diagramUUID, Long stateUUID) {
        Diagram diagram = findDiagramByUUID(diagramUUID);
        State state = stateService.findByStateUUID(stateUUID);
        if (diagram == null) {
            throw new NotFoundException(Diagram.class, diagramUUID);
        }
        if (state == null) {
            throw new NotFoundException(State.class, stateUUID);
        }
        if (!diagram.getStates().contains(state)) {
            LOGGER.info("Diagram[" + diagramUUID + "] not contain State[" + stateUUID + "]. Deleting useless.");
        } else {
            diagram.getStates().remove(state);
        }
        saveDiagram(diagram);
    }

    @Override
    @Transactional
    public Diagram saveExternalDiagram(Diagram diagram) {
        diagram.setUuid(null);
        diagram.getStates().forEach(state -> state.setUuid(null));
        stateService.saveExternalStates(diagram.getStates());
        return diagramRepository.save(diagram);
    }
}
