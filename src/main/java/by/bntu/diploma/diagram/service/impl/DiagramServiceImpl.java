package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.entity.Diagram;
import by.bntu.diploma.diagram.entity.State;
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

import javax.validation.Valid;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DiagramServiceImpl implements DiagramService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiagramServiceImpl.class);

    private DiagramRepository diagramRepo;
    private StateService stateService;

    @Override
    public Diagram findDiagramByUUID(Long diagramUUID) {
        if (diagramUUID == null || diagramUUID < 1) {
            throw new IllegalArgumentException("Diagram UUID is null or less then 1. Got " + diagramUUID);
        }
        return this.diagramRepo.findById(diagramUUID).orElse(null);
    }

    @Override
    @Transactional
    public Diagram saveDiagram(@Valid Diagram diagram) {
        diagram.setStates(this.stateService.saveAllStates(diagram.getStates()));
        return this.diagramRepo.save(diagram);
    }


    @Override
    @Transactional
    public Diagram updateDiagram(@Valid Diagram diagram) {
        Long diagramUUID = diagram.getUuid();
        if (diagramUUID == null || diagramUUID < 1) {
            throw new IllegalArgumentException("Diagram UUID is null or less then 1. Got " + diagramUUID);
        }
        if (!this.diagramRepo.existsById(diagramUUID)) {
            throw new NotFoundException("Diagram[" + diagramUUID + "] not found.");
        }
        return this.saveDiagram(diagram);
    }

    @Override
    @Transactional
    public void deleteDiagramByUUID(Long diagramUUID) {
        if (diagramUUID == null || diagramUUID < 1) {
            throw new IllegalArgumentException("Diagram UUID is null or less then 1.");
        }
        this.diagramRepo.deleteById(diagramUUID);
    }

    @Override
    public List<Diagram> findAllDiagrams() {
        return this.diagramRepo.findAll();
    }

    @Override
    @Transactional
    public Diagram newDiagram() {
        Diagram diagram = Diagram.builder()
                .name("Diagram name")
                .description("Diagram description")
                .build();
        return this.saveDiagram(diagram);
    }

    @Override
    public State newState(Long diagramUUID) {
        Diagram diagram = this.findDiagramByUUID(diagramUUID);
        if (diagram == null) {
            throw new NotFoundException("Diagram[" + diagramUUID + "] not found.");
        }
        State newState = this.stateService.newState();
        diagram.getStates().add(newState);
        this.saveDiagram(diagram);
        return newState;
    }

    @Override
    public void deleteState(Long diagramUUID, Long stateUUID) {
        Diagram diagram = this.findDiagramByUUID(diagramUUID);
        State state = this.stateService.findByStateUUID(stateUUID);
        if (diagram == null) {
            throw new NotFoundException("Diagram[" + diagramUUID + "] not found.");
        }
        if (state == null) {
            throw new NotFoundException("State[" + stateUUID + "] not found.");
        }
        if (!diagram.getStates().contains(state)) {
            LOGGER.info("Diagram[" + diagramUUID + "] not contain State[" + stateUUID + "]. Deleting useless.");
        }
        diagram.getStates().removeIf(s -> s.equals(state));
        this.saveDiagram(diagram);
    }
}
