package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.entity.Diagram;
import by.bntu.diploma.diagram.entity.State;
import by.bntu.diploma.diagram.repository.DiagramRepository;
import by.bntu.diploma.diagram.service.DiagramService;
import by.bntu.diploma.diagram.service.StateService;
import by.bntu.diploma.diagram.service.exception.DiagramNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DiagramServiceImpl implements DiagramService {

    private DiagramRepository diagramRepo;
    private StateService stateService;

    @Override
    public Diagram findDiagramByUUID(Long diagramUUID) {
        if (diagramUUID == null || diagramUUID < 1) {
            throw new IllegalArgumentException();
        }
        return this.diagramRepo.findById(diagramUUID).orElse(null);
    }

    @Override
    @Transactional
    public Diagram save(@Valid Diagram diagram) {
        diagram.setStates(this.stateService.saveAll(diagram.getStates()));
        return this.diagramRepo.save(diagram);
    }


    @Override
    @Transactional
    public Diagram update(@Valid Diagram diagram) {
        Long diagramUUID = diagram.getUuid();
        if (diagramUUID == null || diagramUUID < 1) {
            throw new IllegalArgumentException("Diagram UUID is null or less then 1");
        }
        if (!this.diagramRepo.existsById(diagramUUID)) {
            throw new DiagramNotFoundException("Diagram not found (uuid = " + diagramUUID + ")");
        }
        return this.save(diagram);
    }

    @Override
    @Transactional
    public void deleteDiagramByUUID(Long diagramUUID) {
        if (diagramUUID == null || diagramUUID < 1) {
            throw new IllegalArgumentException("Diagram UUID is null or less then 1");
        }
        this.diagramRepo.deleteById(diagramUUID);
    }

    @Override
    public List<Diagram> findAll() {
        return this.diagramRepo.findAll();
    }

    @Override
    @Transactional
    public Diagram createNewDiagram() {
        Diagram diagram = Diagram.builder()
                .name("Diagram name")
                .description("Diagram description")
                .build();
        return this.save(diagram);
    }

    @Override
    public State newDiagramState(Long diagramUUID) {
        Diagram diagram = this.findDiagramByUUID(diagramUUID);
        if (diagram == null) {
            throw new DiagramNotFoundException("Diagram not found (uuid = " + diagramUUID + ")");
        }
        State newState = this.stateService.newState();
        diagram.getStates().add(newState);
        this.save(diagram);
        return newState;
    }
}
