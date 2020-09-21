package by.bntu.constructor.web;

import by.bntu.constructor.domain.Schema;
import by.bntu.constructor.service.SchemaService;
import by.bntu.constructor.web.dto.SchemaDTO;
import by.bntu.constructor.web.dto.mapper.Mapper;
import by.bntu.constructor.web.util.Format;
import by.bntu.constructor.web.util.converter.format.SchemaDtoConverter;
import by.bntu.constructor.web.util.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/schema/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FormatController {

    private final SchemaService schemaService;
    private final Map<Format, SchemaDtoConverter> formatConverters;
    private final Mapper<Schema, SchemaDTO> schemaMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{uuid}/export")
    public String exportSchema(@PathVariable(name = "uuid") String schemaUuid, @RequestParam("format") Format format) {
        Schema schema = schemaService.findBySchemaUuid(schemaUuid);
        if (schema == null) {
            throw new NotFoundException("Schema[" + schemaUuid + "] not found.");
        }
        SchemaDTO schemaDto = schemaMapper.toDTO(schema);
        return formatConverters.get(format).to(schemaDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/import")
    public SchemaDTO importSchema(@RequestBody String data, @RequestParam("format") Format format) {
        SchemaDTO schemaDto = formatConverters.get(format).from(data);
        Schema schema = schemaMapper.fromDTO(schemaDto);
        schema = schemaService.saveExternalSchema(schema);
        return schemaMapper.toDTO(schema);
    }


}
