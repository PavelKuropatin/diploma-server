package by.bntu.diagram.web;

import by.bntu.diagram.domain.*;
import by.bntu.diagram.service.DiagramService;
import by.bntu.diagram.service.StateService;
import by.bntu.diagram.web.dto.*;
import by.bntu.diagram.web.dto.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/state")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StateController {

    private DiagramService diagramService;
    private StateService stateService;
    private Mapper<Diagram, DiagramDTO> diagramMapper;
    private Mapper<State, StateDTO> stateMapper;
    private Mapper<Variable, VariableDTO> variableMapper;
    private Mapper<Source, SourceDTO> sourceMapper;
    private Mapper<Target, TargetDTO> targetMapper;
    private Mapper<Connection, ConnectionDTO> connectionMapper;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/{uuid}/container/create")
    public StateDTO createVariable(@PathVariable(name = "uuid") String stateUuid,
                                @RequestBody VariableDTO variableDTO) {
        Variable variable = this.variableMapper.fromDTO(variableDTO);
        State state = stateService.putVariable(stateUuid, variable);
        return stateMapper.toDTO(state);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{uuid}/container/delete")
    public StateDTO deleteVariable(@PathVariable(name = "uuid") String stateUuid,
                                   @RequestBody VariableDTO variableDTO) {
        Variable variable = this.variableMapper.fromDTO(variableDTO);
        State state = stateService.deleteVariable(stateUuid, variable);
        return stateMapper.toDTO(state);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{uuid}/source/create")
    public SourceDTO createSource(@PathVariable(name = "uuid") String stateUuid) {
        Source source = stateService.newSource(stateUuid);
        return sourceMapper.toDTO(source);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/{state_uuid}/source/{source_uuid}/delete")
    public DiagramDTO deleteSource(@PathVariable(name = "state_uuid") String stateUuid,
                                   @PathVariable(name = "source_uuid") String sourceUuid) {
        stateService.deleteSource(stateUuid, sourceUuid);
        Diagram diagram = diagramService.findByStateUuid(stateUuid);
        return diagramMapper.toDTO(diagram);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{uuid}/target/create")
    public TargetDTO createTarget(@PathVariable(name = "uuid") String stateUuid) {
        Target target = stateService.newTarget(stateUuid);
        return targetMapper.toDTO(target);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/{state_uuid}/target/{target_uuid}/delete")
    public DiagramDTO deleteTarget(@PathVariable(name = "state_uuid") String stateUuid,
                                   @PathVariable(name = "target_uuid") String targetUuid) {
        stateService.deleteTarget(stateUuid, targetUuid);
        Diagram diagram = diagramService.findByStateUuid(stateUuid);
        return diagramMapper.toDTO(diagram);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{state_uuid}/source/{source_uuid}/connection/create")
    public StateDTO createConnection(@PathVariable(name = "state_uuid") String stateUuid,
                                     @PathVariable(name = "source_uuid") String sourceUuid,
                                     @RequestBody ConnectionDTO connectionDTO) {
        Connection connection = connectionMapper.fromDTO(connectionDTO);
        connection.setSource(Source.builder().uuid(sourceUuid).build());
        State state = stateService.addConnection(stateUuid, connection);
        return stateMapper.toDTO(state);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/{state_uuid}/source/{source_uuid}/connection/delete")
    public StateDTO deleteConnection(@PathVariable(name = "state_uuid") String stateUuid,
                                     @PathVariable(name = "source_uuid") String sourceUuid,
                                     @RequestBody ConnectionDTO connectionDTO) {
        Connection connection = connectionMapper.fromDTO(connectionDTO);
        connection.setSource(Source.builder().uuid(sourceUuid).build());
        State state = stateService.deleteConnection(stateUuid, connection);
        return stateMapper.toDTO(state);

    }
}
