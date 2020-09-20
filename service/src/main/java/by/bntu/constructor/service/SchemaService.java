package by.bntu.constructor.service;

import by.bntu.constructor.domain.Block;
import by.bntu.constructor.domain.Schema;
import by.bntu.constructor.domain.constraint.util.ValidationMessage;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface SchemaService {

    Schema findBySchemaUuid(
            @NotNull(message = ValidationMessage.Schema.UUID_NULL) String schemaUuid
    );

    Schema saveSchema(@Valid Schema schema);

    Schema updateSchema(@Valid Schema schema);

    void deleteSchemaByUuid(
            @NotNull(message = ValidationMessage.Schema.UUID_NULL) String schemaUuid
    );

    List<Schema> findAllSchemas();

    Schema newSchema();

    Block newBLock(
            @NotNull(message = ValidationMessage.Schema.UUID_NULL) String schemaUuid
    );

    Schema deleteBlock(
            @NotNull(message = ValidationMessage.Schema.UUID_NULL) String schemaUuid,
            @NotNull(message = ValidationMessage.Block.UUID_NULL) String blockUuid
    );

    Schema saveExternalSchema(@Valid Schema schema);

    Schema findByBlockUuid(
            @NotNull(message = ValidationMessage.Block.UUID_NULL) String blockUuid
    );
}
