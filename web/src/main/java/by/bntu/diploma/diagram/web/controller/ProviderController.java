package by.bntu.diploma.diagram.web.controller;

import by.bntu.diploma.diagram.domain.Diagram;
import by.bntu.diploma.diagram.domain.Source;
import by.bntu.diploma.diagram.domain.State;
import by.bntu.diploma.diagram.domain.Target;
import by.bntu.diploma.diagram.service.DiagramService;
import by.bntu.diploma.diagram.service.StateService;
import by.bntu.diploma.diagram.web.dto.DiagramDTO;
import by.bntu.diploma.diagram.web.dto.SourceDTO;
import by.bntu.diploma.diagram.web.dto.StateDTO;
import by.bntu.diploma.diagram.web.dto.TargetDTO;
import by.bntu.diploma.diagram.web.dto.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/provider")
public class ProviderController {

    private DiagramService diagramService;
    private StateService stateService;

    private Mapper<Diagram, DiagramDTO> diagramMapper;
    private Mapper<State, StateDTO> stateMapper;
    private Mapper<Source, SourceDTO> sourceMapper;
    private Mapper<Target, TargetDTO> targetMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/diagram")
    public DiagramDTO createDiagram() {
        Diagram diagram = diagramService.newDiagram();
        return diagramMapper.toDTO(diagram);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/diagram/{diagram_uuid}/state")
    public StateDTO createState(@PathVariable(name = "diagram_uuid") Long diagramUUID) {
        State state = diagramService.newState(diagramUUID);
        return stateMapper.toDTO(state);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/state/{state_uuid}/source")
    public SourceDTO createSource(@PathVariable(name = "state_uuid") Long stateUUID) {
        Source source = stateService.newSource(stateUUID);
        return sourceMapper.toDTO(source);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/state/{state_uuid}/target")
    public TargetDTO createTarget(@PathVariable(name = "state_uuid") Long stateUUID) {
        Target target = stateService.newTarget(stateUUID);
        return targetMapper.toDTO(target);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/diagram/{diagram_uuid}")
    public void deleteDiagram(@PathVariable(name = "diagram_uuid") Long diagramUUID) {
        diagramService.deleteDiagramByUUID(diagramUUID);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/diagram/{diagram_uuid}/state/{state_uuid}")
    public void deleteState(@PathVariable(name = "diagram_uuid") Long diagramUUID,
                            @PathVariable(name = "state_uuid") Long stateUUID) {
        diagramService.deleteState(diagramUUID, stateUUID);
    }


    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/state/{state_uuid}/source/{source_uuid}")
    public void deleteSource(@PathVariable(name = "state_uuid") Long stateUUID,
                             @PathVariable(name = "source_uuid") Long sourceUUID) {
        stateService.deleteSource(stateUUID, sourceUUID);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/state/{state_uuid}/target/{target_uuid}")
    public void deleteTarget(@PathVariable(name = "state_uuid") Long stateUUID,
                             @PathVariable(name = "target_uuid") Long targetUUID) {
        stateService.deleteTarget(stateUUID, targetUUID);
    }

}
