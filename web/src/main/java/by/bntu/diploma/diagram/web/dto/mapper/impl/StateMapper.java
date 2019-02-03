package by.bntu.diploma.diagram.web.dto.mapper.impl;

import by.bntu.diploma.diagram.domain.State;
import by.bntu.diploma.diagram.web.dto.StateDTO;
import by.bntu.diploma.diagram.web.dto.mapper.Mapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@org.mapstruct.Mapper(componentModel = "spring", uses = {SourceMapper.class, TargetMapper.class, StyleMapper.class})
public abstract class StateMapper implements Mapper<State, StateDTO> {


    private static final String KEY = "label";
    private static final String VALUE = "value";

    @Mapping(target = "inputContainer", ignore = true)
    @Mapping(target = "outputContainer", ignore = true)
    @Override
    public abstract State fromDTO(StateDTO stateDTO);

    @Mapping(target = "inputContainer", ignore = true)
    @Mapping(target = "outputContainer", ignore = true)
    @Override
    public abstract StateDTO toDTO(State state);

    @AfterMapping
    public void convertContainersForEntity(StateDTO stateDTO, @MappingTarget State state) {
        state.setInputContainer(convert(stateDTO.getInputContainer()));
        state.setOutputContainer(convert(stateDTO.getOutputContainer()));
    }

    @AfterMapping
    public void convertContainersForDTO(State state, @MappingTarget StateDTO stateDTO) {
        stateDTO.setInputContainer(convert(state.getInputContainer()));
        stateDTO.setOutputContainer(convert(state.getOutputContainer()));
    }

    private Map<String, Double> convert(List<Map<String, Object>> other) {
        Map<String, Double> container = new LinkedHashMap<>();
        other.forEach(pair -> {
            String name = pair.get(KEY) + StringUtils.EMPTY;
            String doubleValue = pair.get(VALUE) + StringUtils.EMPTY;
            container.put(name, NumberUtils.toDouble(doubleValue));
        });
        return container;
    }

    private List<Map<String, Object>> convert(Map<String, Double> other) {
        List<Map<String, Object>> container = new LinkedList<>();
        other.forEach((name, value) -> {
            Map<String, Object> pair = new LinkedHashMap<>();
            pair.put(KEY, name);
            pair.put(VALUE, value);
            container.add(pair);
        });
        return container;
    }
}
