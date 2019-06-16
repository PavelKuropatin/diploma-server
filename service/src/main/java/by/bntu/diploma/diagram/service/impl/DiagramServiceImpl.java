package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.domain.Diagram;
import by.bntu.diploma.diagram.domain.State;
import by.bntu.diploma.diagram.repository.DiagramRepository;
import by.bntu.diploma.diagram.service.DiagramService;
import by.bntu.diploma.diagram.service.StateService;
import by.bntu.diploma.diagram.service.exception.NotFoundException;
import by.bntu.diploma.diagram.service.utils.DomainUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DiagramServiceImpl implements DiagramService {

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
    public Diagram deleteState(String diagramUuid, String stateUuid) {
        Diagram diagram = findDiagramByUuid(diagramUuid);
        State state = stateService.findByStateUuid(stateUuid);
        if (diagram == null) {
            throw new NotFoundException(Diagram.class, diagramUuid);
        }
        if (state == null) {
            throw new NotFoundException(State.class, stateUuid);
        }
        if (!diagram.getStates().contains(state)) {
            throw new IllegalArgumentException("Diagram[" + diagramUuid + "] not contain State[" + stateUuid + "]. Deleting useless.");
        }
        DomainUtils.dropSources(diagram.getStates(), state.getSources());
        DomainUtils.dropTargets(diagram.getStates(), state.getTargets());
        diagram.getStates().remove(state);
        diagram = saveDiagram(diagram);
        return diagram;
    }

    @Override
    public Diagram findByStateUuid(String stateUuid) {
        return diagramRepository.findByStateUuid(stateUuid);
    }
}
