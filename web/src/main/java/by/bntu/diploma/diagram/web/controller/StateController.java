package by.bntu.diploma.diagram.web.controller;

import by.bntu.diploma.diagram.domain.ContainerType;
import by.bntu.diploma.diagram.domain.State;
import by.bntu.diploma.diagram.service.StateService;
import by.bntu.diploma.diagram.web.dto.ContainerValueDTO;
import by.bntu.diploma.diagram.web.dto.StateDTO;
import by.bntu.diploma.diagram.web.dto.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/state")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StateController {

    private StateService stateService;
    private Mapper<State, StateDTO> stateMapper;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{uuid}/container")
    public StateDTO putContainerValue(@PathVariable(name = "uuid") Long stateUUID,
                                      @RequestBody ContainerValueDTO containerValue) {
        ContainerType type = containerValue.getType();
        String param = containerValue.getParam();
        Double value = containerValue.getValue();
        State state = this.stateService.putContainerValue(stateUUID, type, param, value);
        return this.stateMapper.toDTO(state);
    }


}
