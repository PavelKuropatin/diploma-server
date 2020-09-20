package by.bntu.constructor.web;

import by.bntu.constructor.domain.Block;
import by.bntu.constructor.domain.Schema;
import by.bntu.constructor.service.SchemaService;
import by.bntu.constructor.web.dto.BlockDTO;
import by.bntu.constructor.web.dto.SchemaDTO;
import by.bntu.constructor.web.dto.ViewSchemaDTO;
import by.bntu.constructor.web.dto.mapper.Mapper;
import by.bntu.constructor.web.util.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/schema")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SchemaController {

    private final SchemaService schemaService;
    private final Mapper<Schema, SchemaDTO> schemaMapper;
    private final Mapper<Schema, ViewSchemaDTO> viewSchemaMapper;
    private final Mapper<Block, BlockDTO> blockMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "/{uuid}")
    public SchemaDTO updateSchema(@PathVariable(name = "uuid") String schemaUuid,
                                  @RequestBody SchemaDTO schemaDTO) {
        Schema schema = schemaMapper.fromDTO(schemaDTO);
        schema.setUuid(schemaUuid);
        schema = schemaService.updateSchema(schema);
        return schemaMapper.toDTO(schema);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{uuid}")
    public SchemaDTO findSchemaByUuid(@PathVariable(name = "uuid") String schemaUuid) {
        Schema schema = schemaService.findBySchemaUuid(schemaUuid);
        if (schema == null) {
            throw new NotFoundException("Schema[" + schemaUuid + "] not found.");
        }
        return schemaMapper.toDTO(schema);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ViewSchemaDTO> getSchemasInfo() {
        return schemaService.findAllSchemas()
                .stream()
                .map(viewSchemaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping
    public SchemaDTO saveSchema(@RequestParam(name = "external",
            required = false, defaultValue = "false") Boolean isExternal,
                                @RequestBody SchemaDTO schemaDTO) {
        Schema schema;
        schema = schemaMapper.fromDTO(schemaDTO);

        if (isExternal) {
            schema = schemaService.saveExternalSchema(schema);
        } else {
            schema = schemaService.saveSchema(schema);
        }

        return schemaMapper.toDTO(schema);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{uuid}/block")
    public BlockDTO createBlock(@PathVariable(name = "uuid") String schemaUuid) {
        Block block = schemaService.newBLock(schemaUuid);
        return blockMapper.toDTO(block);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(value = "/{schema_uuid}/block/{block_uuid}")
    public SchemaDTO deleteBlock(@PathVariable(name = "schema_uuid") String schemaUuid,
                                 @PathVariable(name = "block_uuid") String blockUuid) {
        Schema schema = schemaService.deleteBlock(schemaUuid, blockUuid);
        return schemaMapper.toDTO(schema);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/new")
    public SchemaDTO createSchema() {
        Schema schema = schemaService.newSchema();
        return schemaMapper.toDTO(schema);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(value = "/{schema_uuid}")
    public void deleteSchema(@PathVariable(name = "schema_uuid") String schemaUuid) {
        schemaService.deleteSchemaByUuid(schemaUuid);
    }
}
