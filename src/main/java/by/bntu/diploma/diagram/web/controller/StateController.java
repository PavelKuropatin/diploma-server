package by.bntu.diploma.diagram.web.controller;

import by.bntu.diploma.diagram.entity.ContainerType;
import by.bntu.diploma.diagram.entity.State;
import by.bntu.diploma.diagram.service.StateService;
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
                                      @RequestParam(name = "type") ContainerType type,
                                      @RequestParam(name = "param") String param,
                                      @RequestParam(name = "value", required = false, defaultValue = "0") Double value) {
        State state = this.stateService.putContainerValue(stateUUID, type, param, value);
        return this.stateMapper.toDTO(state);
    }

}
