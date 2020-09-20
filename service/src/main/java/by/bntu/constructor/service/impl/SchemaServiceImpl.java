package by.bntu.constructor.service.impl;

import by.bntu.constructor.domain.Schema;
import by.bntu.constructor.domain.Block;
import by.bntu.constructor.repository.SchemaRepository;
import by.bntu.constructor.service.SchemaService;
import by.bntu.constructor.service.BlockService;
import by.bntu.constructor.service.utils.DomainUtils;
import by.bntu.constructor.service.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SchemaServiceImpl implements SchemaService {

    private final SchemaRepository schemaRepository;
    private final BlockService blockService;

    @Override
    public Schema findBySchemaUuid(String schemaUuid) {
        return schemaRepository.findById(schemaUuid).orElse(null);
    }

    @Override
    @Transactional
    public Schema saveSchema(Schema schema) {
        blockService.saveAllBlocks(schema.getBlocks());
        return schemaRepository.save(schema);
    }

    @Override
    @Transactional
    public Schema updateSchema(Schema schema) {
        String schemaUuid = schema.getUuid();
        if (!schemaRepository.existsById(schemaUuid)) {
            throw new NotFoundException(Schema.class, schemaUuid);
        }
        return saveSchema(schema);
    }

    @Override
    @Transactional
    public Schema saveExternalSchema(Schema schema) {
        schema.setUuid(null);
        schema.getBlocks().forEach(block -> block.setUuid(null));
        blockService.saveExternalBlocks(schema.getBlocks());
        return schemaRepository.save(schema);
    }

    @Override
    @Transactional
    public void deleteSchemaByUuid(String schemaUuid) {
        schemaRepository.deleteById(schemaUuid);
    }

    @Override
    public List<Schema> findAllSchemas() {
        return schemaRepository.findAll();
    }

    @Override
    @Transactional
    public Schema newSchema() {
        Schema schema = Schema.builder()
                .name("Block name")
                .description("Block description")
                .build();
        return saveSchema(schema);
    }

    @Override
    @Transactional
    public Block newBLock(String schemaUuid) {
        Schema schema = findBySchemaUuid(schemaUuid);
        if (schema == null) {
            throw new NotFoundException(Schema.class, schemaUuid);
        }
        Block newBlock = blockService.newBlock();
        schema.getBlocks().add(newBlock);
        saveSchema(schema);
        return newBlock;
    }

    @Override
    @Transactional
    public Schema deleteBlock(String schemaUuid, String blockUuid) {
        Schema schema = findBySchemaUuid(schemaUuid);
        Block block = blockService.findByBlockUuid(blockUuid);
        if (schema == null) {
            throw new NotFoundException(Schema.class, schemaUuid);
        }
        if (block == null) {
            throw new NotFoundException(Block.class, blockUuid);
        }
        if (!schema.getBlocks().contains(block)) {
            throw new IllegalArgumentException("Block[" + schemaUuid + "] not contain block[" + blockUuid + "]. Deleting useless.");
        }
        DomainUtils.dropInputs(schema.getBlocks(), block.getInputs());
        DomainUtils.dropOutputs(schema.getBlocks(), block.getOutputs());
        schema.getBlocks().remove(block);
        schema = saveSchema(schema);
        return schema;
    }

    @Override
    public Schema findByBlockUuid(String blockUuid) {
        return schemaRepository.findByBlockUuid(blockUuid);
    }
}
