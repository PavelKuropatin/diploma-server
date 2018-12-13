package by.bntu.diploma.diagram.web.controller;

import by.bntu.diploma.diagram.entity.Diagram;
import by.bntu.diploma.diagram.entity.SourceEndpoint;
import by.bntu.diploma.diagram.entity.State;
import by.bntu.diploma.diagram.entity.TargetEndpoint;
import by.bntu.diploma.diagram.service.DiagramService;
import by.bntu.diploma.diagram.service.StateService;
import by.bntu.diploma.diagram.web.dto.DiagramDTO;
import by.bntu.diploma.diagram.web.dto.SourceEndpointDTO;
import by.bntu.diploma.diagram.web.dto.StateDTO;
import by.bntu.diploma.diagram.web.dto.TargetEndpointDTO;
import by.bntu.diploma.diagram.web.dto.mapper.impl.DiagramMapper;
import by.bntu.diploma.diagram.web.dto.mapper.impl.SourceEndpointMapper;
import by.bntu.diploma.diagram.web.dto.mapper.impl.StateMapper;
import by.bntu.diploma.diagram.web.dto.mapper.impl.TargetEndpointMapper;
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
    private SourceEndpointMapper sourceEndpointMapper;
    private TargetEndpointMapper targetEndpointMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public DiagramDTO createNewDiagram() {
        Diagram diagram = this.diagramService.createNewDiagram();
        return this.diagramMapper.toDTO(diagram);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/diagram/{diagram_uuid}/state")
    public StateDTO createNewState(@PathVariable(name = "diagram_uuid") Long diagramUUID) {
        State state = this.diagramService.newDiagramState(diagramUUID);
        return this.stateMapper.toDTO(state);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/state/{state_uuid}/source")
    public SourceEndpointDTO createNewSourceEndpoint(@PathVariable(name = "state_uuid") Long stateUUID) {
        SourceEndpoint sourceEndpoint = this.stateService.newStateSourceEndpoint(stateUUID);
        return this.sourceEndpointMapper.toDTO(sourceEndpoint);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/state/{state_uuid}/target")
    public TargetEndpointDTO createNewTargetEndpoint(@PathVariable(name = "state_uuid") Long stateUUID) {
        TargetEndpoint targetEndpoint = this.stateService.newStateTargetEndpoint(stateUUID);
        return this.targetEndpointMapper.toDTO(targetEndpoint);
    }


}
