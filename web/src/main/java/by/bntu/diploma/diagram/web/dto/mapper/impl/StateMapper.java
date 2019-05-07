package by.bntu.diploma.diagram.web.dto.mapper.impl;

import by.bntu.diploma.diagram.domain.State;
import by.bntu.diploma.diagram.domain.Variable;
import by.bntu.diploma.diagram.web.dto.StateDTO;
import by.bntu.diploma.diagram.web.dto.mapper.Mapper;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@org.mapstruct.Mapper(componentModel = "spring", uses = {SourceMapper.class, TargetMapper.class, StyleMapper.class})
public abstract class StateMapper implements Mapper<State, StateDTO> {


    private static final String KEY = "label";
    private static final String VALUE = "value";
    private static final String FUNCTION = "function";

    @Mapping(target = "inputContainer", ignore = true)
    @Mapping(target = "outputContainer", ignore = true)
    @Override
    public abstract State fromDTO(StateDTO stateDTO);

    @Mapping(target = "inputContainer", ignore = true)
    @Mapping(target = "outputContainer", ignore = true)
    @Override
    public abstract StateDTO toDTO(State state);

    @AfterMapping
    void convertContainersForEntity(StateDTO stateDTO, @MappingTarget State state) {
        state.setInputContainer(convertToVariables(stateDTO.getInputContainer()));
        state.setOutputContainer(convertToVariables(stateDTO.getOutputContainer()));
    }

    @AfterMapping
    void convertContainersForDTO(State state, @MappingTarget StateDTO stateDTO) {
        stateDTO.setInputContainer(convertFromVariables(state.getInputContainer()));
        stateDTO.setOutputContainer(convertFromVariables(state.getOutputContainer()));
    }

    private List<Variable> convertToVariables(List<Map<String, Object>> vars) {
        return vars.stream().map(var -> {
            String param = var.get(KEY) + StringUtils.EMPTY;
            Double value = Double.parseDouble(var.get(VALUE) + StringUtils.EMPTY);
            String function = null;
            if (var.getOrDefault(FUNCTION, null) != null) {
                function = var.get(FUNCTION) + StringUtils.EMPTY;
            }
            return Variable.builder()
                    .param(param)
                    .value(value)
                    .function(function)
                    .build();
        }).collect(Collectors.toList());
    }

    private List<Map<String, Object>> convertFromVariables(List<Variable> vars) {
        return vars.stream().map(var -> {
            Map<String, Object> outputVar = new LinkedHashMap<>();
            outputVar.put(KEY, var.getParam());
            outputVar.put(VALUE, var.getValue());
            if (var.getFunction() != null) {
                outputVar.put(FUNCTION, var.getFunction());
            }
            return outputVar;
        }).collect(Collectors.toList());
    }
}
