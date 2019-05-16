package by.bntu.diploma.diagram.web.controller;

import by.bntu.diploma.diagram.domain.State;
import by.bntu.diploma.diagram.domain.Variable;
import by.bntu.diploma.diagram.service.StateService;
import by.bntu.diploma.diagram.web.dto.StateDTO;
import by.bntu.diploma.diagram.web.dto.VariableDTO;
import by.bntu.diploma.diagram.web.dto.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/state")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StateController {

    private StateService stateService;
    private Mapper<State, StateDTO> stateMapper;
    private Mapper<Variable, VariableDTO> variableMapper;
    private Converter<String, Variable.Type> converter;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{uuid}/container")
    public StateDTO putVariable(@PathVariable(name = "uuid") String stateUuid,
                                @RequestBody VariableDTO variableDTO) {
        Variable.Type type = converter.convert(variableDTO.getType());
        Variable variable = this.variableMapper.fromDTO(variableDTO);
        State state = stateService.putVariable(stateUuid, type, variable);
        return stateMapper.toDTO(state);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{uuid}/container")
    public StateDTO dropVariable(@PathVariable(name = "uuid") String stateUuid,
                                 @RequestBody VariableDTO variableDTO) {
        String param = variableDTO.getParam();
        Variable.Type type = converter.convert(variableDTO.getType());
        State state = stateService.deleteVariable(stateUuid, type, param);
        return stateMapper.toDTO(state);
    }


}
