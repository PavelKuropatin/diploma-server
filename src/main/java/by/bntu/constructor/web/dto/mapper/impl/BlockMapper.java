package by.bntu.constructor.web.dto.mapper.impl;

import by.bntu.constructor.domain.Block;
import by.bntu.constructor.domain.Connection;
import by.bntu.constructor.domain.Input;
import by.bntu.constructor.domain.Variable;
import by.bntu.constructor.web.dto.BlockDTO;
import by.bntu.constructor.web.dto.ConnectionDTO;
import by.bntu.constructor.web.dto.InputDTO;
import by.bntu.constructor.web.dto.mapper.Mapper;
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

@org.mapstruct.Mapper(componentModel = "spring", uses = {InputMapper.class, OutputMapper.class, StyleMapper.class, SettingsMapper.class})
public abstract class BlockMapper implements Mapper<Block, BlockDTO> {

    private static final String KEY = "label";
    private static final String VALUE = "value";
    private static final String FUNCTION = "expression";

    @Autowired
    private Mapper<Connection, ConnectionDTO> connectionMapper;

    @Autowired
    private Mapper<Input, InputDTO> inputMapper;

    @Mapping(target = "inputVars", ignore = true)
    @Mapping(target = "outputVars", ignore = true)
    @Mapping(target = "connections", ignore = true)
    @Override
    public abstract Block fromDTO(BlockDTO blockDTO);

    @Mapping(target = "inputVars", ignore = true)
    @Mapping(target = "outputVars", ignore = true)
    @Override
    public abstract BlockDTO toDTO(Block block);

    @BeforeMapping
    void setDefaultsToDTO(BlockDTO blockDTO, @MappingTarget Block block) {
        if (blockDTO.getInputVars() == null) {
            blockDTO.setInputVars(Collections.emptyList());
        }
        if (blockDTO.getOutputVars() == null) {
            blockDTO.setOutputVars(Collections.emptyList());
        }
        if (blockDTO.getInputs() == null) {
            blockDTO.setInputs(Collections.emptyList());
        }
        if (blockDTO.getOutputs() == null) {
            blockDTO.setOutputs(Collections.emptyList());
        }
        blockDTO.getInputs().forEach(inputDTO -> {
            if (inputDTO.getConnections() == null) {
                inputDTO.setConnections(Collections.emptyList());
            }
        });
    }

    @BeforeMapping
    void setDefaultsToEntity(Block block, @MappingTarget BlockDTO blockDTO) {
        if (block.getInputVars() == null) {
            block.setInputVars(Collections.emptyList());
        }
        if (block.getOutputVars() == null) {
            block.setOutputVars(Collections.emptyList());
        }
        if (block.getInputs() == null) {
            block.setInputs(Collections.emptyList());
        }
        if (block.getConnections() == null) {
            block.setConnections(Collections.emptyList());
        }
        if (block.getOutputs() == null) {
            block.setOutputs(Collections.emptyList());
        }
    }

    @AfterMapping
    void convertVarsForEntity(BlockDTO blockDTO, @MappingTarget Block block) {
        block.setInputVars(convertToVariables(blockDTO.getInputVars(), Variable.Type.INPUT));
        block.setOutputVars(convertToVariables(blockDTO.getOutputVars(), Variable.Type.OUTPUT));
        convertConnectionsForEntity(blockDTO, block);
    }

    @AfterMapping
    void convertVarsForDTO(Block block, @MappingTarget BlockDTO blockDTO) {
        blockDTO.setInputVars(convertFromVariables(block.getInputVars()));
        blockDTO.setOutputVars(convertFromVariables(block.getOutputVars()));
        convertConnectionsForDTO(block, blockDTO);
    }

    private void convertConnectionsForDTO(Block block, BlockDTO blockDTO) {
        Map<Input, List<Connection>> groupConnections = block.getConnections().stream()
                .collect(groupingBy(Connection::getInput));
        Map<Input, List<ConnectionDTO>> groupDtoConnections = groupConnections
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream()
                        .map(connectionMapper::toDTO).collect(Collectors.toList())));
        List<InputDTO> dtoInputs = block.getInputs().stream()
                .map(input ->
                        InputDTO.builder()
                                .uuid(input.getUuid())
                                .connections(groupDtoConnections.getOrDefault(input, Collections.emptyList()))
                                .build())
                .collect(Collectors.toList());
        blockDTO.setInputs(dtoInputs);
    }

    private void convertConnectionsForEntity(BlockDTO blockDTO, Block block) {
        List<Connection> blockConnections = blockDTO.getInputs().stream()
                .map(inputDTO -> {
                    Input input = inputMapper.fromDTO(inputDTO);
                    List<Connection> connections = inputDTO.getConnections().stream()
                            .map(connectionMapper::fromDTO)
                            .collect(Collectors.toList());
                    connections.forEach(connection -> connection.setInput(input));
                    return connections;
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
        block.setConnections(blockConnections);
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
