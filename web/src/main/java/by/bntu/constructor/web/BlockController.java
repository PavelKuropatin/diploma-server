package by.bntu.constructor.web;

import by.bntu.constructor.domain.*;
import by.bntu.constructor.service.BlockService;
import by.bntu.constructor.service.SchemaService;
import by.bntu.constructor.web.dto.*;
import by.bntu.constructor.web.dto.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/block")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BlockController {

    private final SchemaService schemaService;
    private final BlockService blockService;
    private final Mapper<Schema, SchemaDTO> schemaMapper;
    private final Mapper<Block, BlockDTO> blockMapper;
    private final Mapper<Variable, VariableDTO> variableMapper;
    private final Mapper<Input, InputDTO> inputMapper;
    private final Mapper<Output, OutputDTO> outputMapper;
    private final Mapper<Connection, ConnectionDTO> connectionMapper;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/{uuid}/vars/create", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public BlockDTO createVariable(@PathVariable(name = "uuid") String blockUuid,
                                   @RequestBody VariableDTO variableDTO) {
        Variable variable = this.variableMapper.fromDTO(variableDTO);
        Block block = blockService.putVar(blockUuid, variable);
        return blockMapper.toDTO(block);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/{uuid}/vars/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public BlockDTO deleteVariable(@PathVariable(name = "uuid") String blockUuid,
                                   @RequestBody VariableDTO variableDTO) {
        Variable variable = this.variableMapper.fromDTO(variableDTO);
        Block block = blockService.deleteVar(blockUuid, variable);
        return blockMapper.toDTO(block);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{uuid}/input/create", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public InputDTO createInput(@PathVariable(name = "uuid") String blockUuid) {
        Input input = blockService.newInput(blockUuid);
        return inputMapper.toDTO(input);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/{block_uuid}/input/{input_uuid}/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public SchemaDTO deleteInput(@PathVariable(name = "block_uuid") String blockUuid,
                                 @PathVariable(name = "input_uuid") String inputUuid) {
        blockService.deleteInput(blockUuid, inputUuid);
        Schema schema = schemaService.findByBlockUuid(blockUuid);
        return schemaMapper.toDTO(schema);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{uuid}/output/create", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public OutputDTO createOutput(@PathVariable(name = "uuid") String blockUuid) {
        Output output = blockService.newOutput(blockUuid);
        return outputMapper.toDTO(output);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/{block_uuid}/output/{output_uuid}/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public SchemaDTO deleteOutput(@PathVariable(name = "block_uuid") String blockUuid,
                                  @PathVariable(name = "output_uuid") String outputUuid) {
        blockService.deleteOutput(blockUuid, outputUuid);
        Schema schema = schemaService.findByBlockUuid(blockUuid);
        return schemaMapper.toDTO(schema);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{block_uuid}/input/{input_uuid}/connection/create", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public BlockDTO createConnection(@PathVariable(name = "block_uuid") String blockUuid,
                                     @PathVariable(name = "input_uuid") String inputUuid,
                                     @RequestBody ConnectionDTO connectionDTO) {
        Connection connection = connectionMapper.fromDTO(connectionDTO);
        connection.setInput(Input.builder().uuid(inputUuid).build());
        Block block = blockService.addConnection(blockUuid, connection);
        return blockMapper.toDTO(block);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/{block_uuid}/input/{input_uuid}/connection/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public BlockDTO deleteConnection(@PathVariable(name = "block_uuid") String blockUuid,
                                     @PathVariable(name = "input_uuid") String inputUuid,
                                     @RequestBody ConnectionDTO connectionDTO) {
        Connection connection = connectionMapper.fromDTO(connectionDTO);
        connection.setInput(Input.builder().uuid(inputUuid).build());
        Block block = blockService.deleteConnection(blockUuid, connection);
        return blockMapper.toDTO(block);

    }
}
