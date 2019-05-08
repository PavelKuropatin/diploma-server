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
    public Diagram findDiagramByUuid(String diagramUuid) {
        return diagramRepository.findById(diagramUuid).orElse(null);
    }

    @Override
    @Transactional
    public Diagram saveDiagram(Diagram diagram) {
        stateService.saveAllStates(diagram.getStates());
        return diagramRepository.save(diagram);
    }

    @Override
    @Transactional
    public Diagram updateDiagram(Diagram diagram) {
        String diagramUuid = diagram.getUuid();
        if (!diagramRepository.existsById(diagramUuid)) {
            throw new NotFoundException(Diagram.class, diagramUuid);
        }
        return saveDiagram(diagram);
    }

    @Override
    @Transactional
    public Diagram saveExternalDiagram(Diagram diagram) {
        diagram.setUuid(null);
        diagram.getStates().forEach(state -> state.setUuid(null));
        stateService.saveExternalStates(diagram.getStates());
        return diagramRepository.save(diagram);
    }

    @Override
    @Transactional
    public void deleteDiagramByUuid(String diagramUuid) {
        diagramRepository.deleteById(diagramUuid);
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
    public State newState(String diagramUuid) {
        Diagram diagram = findDiagramByUuid(diagramUuid);
        if (diagram == null) {
            throw new NotFoundException(Diagram.class, diagramUuid);
        }
        State newState = stateService.newState();
        diagram.getStates().add(newState);
        saveDiagram(diagram);
        return newState;
    }

    @Override
    @Transactional
    public void deleteState(String diagramUuid, String stateUuid) {
        Diagram diagram = findDiagramByUuid(diagramUuid);
        State state = stateService.findByStateUuid(stateUuid);
        if (diagram == null) {
            throw new NotFoundException(Diagram.class, diagramUuid);
        }
        if (state == null) {
            throw new NotFoundException(State.class, stateUuid);
        }
        if (!diagram.getStates().contains(state)) {
            LOGGER.info("Diagram[" + diagramUuid + "] not contain State[" + stateUuid + "]. Deleting useless.");
        } else {
            diagram.getStates().remove(state);
        }
        saveDiagram(diagram);
    }


}
