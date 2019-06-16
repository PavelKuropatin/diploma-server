package by.bntu.diagram.web.dto.mapper.impl;

import by.bntu.diagram.domain.Connection;
import by.bntu.diagram.domain.Source;
import by.bntu.diagram.domain.State;
import by.bntu.diagram.domain.Variable;
import by.bntu.diagram.web.dto.ConnectionDTO;
import by.bntu.diagram.web.dto.SourceDTO;
import by.bntu.diagram.web.dto.StateDTO;
import by.bntu.diagram.web.dto.mapper.Mapper;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@org.mapstruct.Mapper(componentModel = "spring", uses = {SourceMapper.class, TargetMapper.class, StyleMapper.class})
public abstract class StateMapper implements Mapper<State, StateDTO> {

    private static final String KEY = "label";
    private static final String VALUE = "value";
    private static final String FUNCTION = "stringFunction";

    @Autowired
    private Mapper<Connection, ConnectionDTO> connectionMapper;

    @Autowired
    private Mapper<Source, SourceDTO> sourceMapper;

    @Mapping(target = "inputContainer", ignore = true)
    @Mapping(target = "outputContainer", ignore = true)
    @Mapping(target = "connections", ignore = true)
    @Override
    public abstract State fromDTO(StateDTO stateDTO);

    @Mapping(target = "inputContainer", ignore = true)
    @Mapping(target = "outputContainer", ignore = true)
    @Override
    public abstract StateDTO toDTO(State state);

    @BeforeMapping
    void setDefaultsToDTO(StateDTO stateDTO, @MappingTarget State state) {
        if (stateDTO.getInputContainer() == null) {
            stateDTO.setInputContainer(Collections.emptyList());
        }
        if (stateDTO.getOutputContainer() == null) {
            stateDTO.setOutputContainer(Collections.emptyList());
        }
        if (stateDTO.getSources() == null) {
            stateDTO.setSources(Collections.emptyList());
        }
        if (stateDTO.getTargets() == null) {
            stateDTO.setTargets(Collections.emptyList());
        }
        stateDTO.getSources().forEach(sourceDTO -> {
            if (sourceDTO.getConnections() == null) {
                sourceDTO.setConnections(Collections.emptyList());
            }
        });
    }

    @BeforeMapping
    void setDefaultsToEntity(State state, @MappingTarget StateDTO stateDTO) {
        if (state.getInputContainer() == null) {
            state.setInputContainer(Collections.emptyList());
        }
        if (state.getOutputContainer() == null) {
            state.setOutputContainer(Collections.emptyList());
        }
        if (state.getSources() == null) {
            state.setSources(Collections.emptyList());
        }
        if (state.getConnections() == null) {
            state.setConnections(Collections.emptyList());
        }
        if (state.getTargets() == null) {
            state.setTargets(Collections.emptyList());
        }
    }

    @AfterMapping
    void convertContainersForEntity(StateDTO stateDTO, @MappingTarget State state) {
        state.setInputContainer(convertToVariables(stateDTO.getInputContainer(), Variable.Type.INPUT));
        state.setOutputContainer(convertToVariables(stateDTO.getOutputContainer(), Variable.Type.OUTPUT));
        convertConnectionsForEntity(stateDTO, state);
    }

    @AfterMapping
    void convertContainersForDTO(State state, @MappingTarget StateDTO stateDTO) {
        stateDTO.setInputContainer(convertFromVariables(state.getInputContainer()));
        stateDTO.setOutputContainer(convertFromVariables(state.getOutputContainer()));
        convertConnectionsForDTO(state, stateDTO);
    }

    private void convertConnectionsForDTO(State state, StateDTO stateDTO) {
        Map<Source, List<Connection>> groupConnections = state.getConnections().stream()
                .collect(groupingBy(Connection::getSource));
        Map<Source, List<ConnectionDTO>> groupDtoConnections = groupConnections
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream()
                        .map(connectionMapper::toDTO).collect(Collectors.toList())));
        List<SourceDTO> dtoSources = state.getSources().stream()
                .map(source ->
                        SourceDTO.builder()
                                .uuid(source.getUuid())
                                .connections(groupDtoConnections.getOrDefault(source, Collections.emptyList()))
                                .build())
                .collect(Collectors.toList());
        stateDTO.setSources(dtoSources);
    }

    private void convertConnectionsForEntity(StateDTO stateDTO, State state) {
        List<Connection> stateConnections = stateDTO.getSources().stream()
                .map(sourceDTO -> {
                    Source source = sourceMapper.fromDTO(sourceDTO);
                    List<Connection> connections = sourceDTO.getConnections().stream()
                            .map(connectionMapper::fromDTO)
                            .collect(Collectors.toList());
                    connections.forEach(connection -> connection.setSource(source));
                    return connections;
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
        state.setConnections(stateConnections);
    }

    private List<Variable> convertToVariables(List<Map<String, Object>> vars, Variable.Type type) {
        return vars.stream().map(var -> {
            String param = var.get(KEY) + StringUtils.EMPTY;
            Object objValue = var.get(VALUE);
            Double value = Double.parseDouble((objValue != null ? objValue : 0) + StringUtils.EMPTY);
            String function = null;
            if (var.getOrDefault(FUNCTION, null) != null) {
                function = var.get(FUNCTION) + StringUtils.EMPTY;
            }
            return Variable.builder()
                    .param(param)
                    .value(value)
                    .type(type)
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
