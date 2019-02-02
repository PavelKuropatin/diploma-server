package by.bntu.diploma.diagram.web.controller;

import by.bntu.diploma.diagram.entity.Diagram;
import by.bntu.diploma.diagram.entity.Source;
import by.bntu.diploma.diagram.entity.State;
import by.bntu.diploma.diagram.entity.Target;
import by.bntu.diploma.diagram.service.DiagramService;
import by.bntu.diploma.diagram.service.StateService;
import by.bntu.diploma.diagram.web.dto.DiagramDTO;
import by.bntu.diploma.diagram.web.dto.SourceDTO;
import by.bntu.diploma.diagram.web.dto.StateDTO;
import by.bntu.diploma.diagram.web.dto.TargetDTO;
import by.bntu.diploma.diagram.web.dto.mapper.impl.DiagramMapper;
import by.bntu.diploma.diagram.web.dto.mapper.impl.SourceMapper;
import by.bntu.diploma.diagram.web.dto.mapper.impl.StateMapper;
import by.bntu.diploma.diagram.web.dto.mapper.impl.TargetMapper;
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

    private DiagramMapper diagramMapper;
    private StateMapper stateMapper;
    private SourceMapper sourceMapper;
    private TargetMapper targetMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/diagram")
    public DiagramDTO createDiagram() {
        Diagram diagram = this.diagramService.newDiagram();
        return this.diagramMapper.toDTO(diagram);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/diagram/{diagram_uuid}/state")
    public StateDTO createState(@PathVariable(name = "diagram_uuid") Long diagramUUID) {
        State state = this.diagramService.newState(diagramUUID);
        return this.stateMapper.toDTO(state);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/state/{state_uuid}/source")
    public SourceDTO createSource(@PathVariable(name = "state_uuid") Long stateUUID) {
        Source source = this.stateService.newSource(stateUUID);
        return this.sourceMapper.toDTO(source);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/state/{state_uuid}/target")
    public TargetDTO createTarget(@PathVariable(name = "state_uuid") Long stateUUID) {
        Target target = this.stateService.newTarget(stateUUID);
        return this.targetMapper.toDTO(target);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/diagram/{diagram_uuid}")
    public void deleteDiagram(@PathVariable(name = "diagram_uuid") Long diagramUUID) {
        this.diagramService.deleteDiagramByUUID(diagramUUID);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/diagram/{diagram_uuid}/state/{state_uuid}")
    public void deleteState(@PathVariable(name = "diagram_uuid") Long diagramUUID,
                            @PathVariable(name = "state_uuid") Long stateUUID) {
        this.diagramService.deleteState(diagramUUID, stateUUID);
    }


    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/state/{state_uuid}/source/{source_uuid}")
    public void deleteSource(@PathVariable(name = "state_uuid") Long stateUUID,
                             @PathVariable(name = "source_uuid") Long sourceUUID) {
        this.stateService.deleteSource(stateUUID, sourceUUID);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/state/{state_uuid}/target/{target_uuid}")
    public void deleteTarget(@PathVariable(name = "state_uuid") Long stateUUID,
                             @PathVariable(name = "target_uuid") Long targetUUID) {
        this.stateService.deleteTarget(stateUUID, targetUUID);
    }

}
