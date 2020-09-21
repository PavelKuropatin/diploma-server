package by.bntu.constructor.service.impl;

import by.bntu.constructor.domain.Block;
import by.bntu.constructor.domain.Schema;
import by.bntu.constructor.repository.SchemaRepository;
import by.bntu.constructor.repository.BlockRepository;
import by.bntu.constructor.service.SchemaService;
import by.bntu.constructor.service.BlockService;
import by.bntu.constructor.service.exception.NotFoundException;
import by.bntu.constructor.service.impl.config.TestConfig;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;


@SpringJUnitConfig(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class IntegrationSchemaServiceImplTest {

    private static final String VALID_STR = RandomStringUtils.random(127, true, true);
    private static final String RANDOM_UUID = UUID.randomUUID().toString();

    @Autowired
    private SchemaService schemaService;

    @Autowired
    private SchemaRepository schemaRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private BlockService blockService;

    private Schema schema;

    private static Stream<Arguments> provideExternalUuids() {
        return Stream.of(
                Arguments.of((String) null),
                Arguments.of(RANDOM_UUID)
        );
    }

    @BeforeEach
    void setUp() {
        schema = Schema.builder()
                .name(VALID_STR)
                .description(VALID_STR)
                .build();
    }

    @Test
    @DisplayName("find by existent uuid")
    void findSchemaByUuid_existentUuid_returnObj() {
        Schema expected = schemaService.saveSchema(schema);
        Schema actual = schemaService.findBySchemaUuid(expected.getUuid());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("save valid schema")
    void saveSchema_validObj_returnObj() {
        assertNull(schema.getUuid());
        assertEquals(0, schemaRepository.count());
        schemaService.saveSchema(schema);
        assertNotNull(schema.getUuid());
        assertEquals(1, schemaRepository.count());
    }

    @Test
    @DisplayName("update valid schema")
    void updateSchema_validObj_returnObj() {
        schemaService.saveSchema(schema);
        assertEquals(VALID_STR, schema.getName());

        String newName = RandomStringUtils.random(127, true, true);
        schema.setName(newName);

        schemaService.updateSchema(schema);
        assertEquals(schema.getName(), newName);

    }

    @Test
    @DisplayName("delete existent schema")
    void deleteSchemaByUuid_validUuid_returnObj() {
        schemaService.saveSchema(schema);
        assertEquals(1, schemaRepository.count());
        schemaService.deleteSchemaByUuid(schema.getUuid());
        assertEquals(0, schemaRepository.count());
    }

    @Test
    @DisplayName("find all schemas")
    void findAllSchemas__returnObj() {
        schemaService.saveSchema(schema);
        List<Schema> schemas = schemaService.findAllSchemas();
        assertEquals(1, schemaRepository.count());
        assertThat(schemas, hasItem(schema));
    }

    @Test
    @DisplayName("new schema")
    void newSchema__returnObj() {
        assertEquals(0, schemaRepository.count());
        Schema schema = schemaService.newSchema();
        assertNotNull(schema.getUuid());
        assertEquals(1, schemaRepository.count());
    }

    @Test
    @DisplayName("new block for existent schema")
    void newBlock__returnObj() {
        schemaService.saveSchema(schema);
        assertEquals(0, blockRepository.count());
        Block block = schemaService.newBLock(schema.getUuid());
        assertNotNull(block.getUuid());
        assertEquals(1, blockRepository.count());
    }

    @Test
    @DisplayName("delete block by existent block uuid for existent schema")
    void deleteBlock__existentObj_returnObj() {
        schemaService.saveSchema(schema);
        Block block = schemaService.newBLock(schema.getUuid());

        assertEquals(1, schema.getBlocks().size());

        schemaService.deleteBlock(schema.getUuid(), block.getUuid());

        assertEquals(0, schema.getBlocks().size());

    }

    @Test
    @DisplayName("delete block by existent block uuid for not allowing existent schema")
    void deleteBlock_existentBlockAndNotAllowingSchema_returnObj() {
        schemaService.saveSchema(schema);
        schemaService.newBLock(schema.getUuid());
        Block block = blockService.newBlock();

        assertEquals(1, schema.getBlocks().size());

        assertThrows(IllegalArgumentException.class, () -> schemaService.deleteBlock(schema.getUuid(), block.getUuid()));

        assertEquals(1, schema.getBlocks().size());

    }

    @Test
    @DisplayName("delete by non existent block uuid for existent schema")
    void deleteBlock_nonExistentBlock_returnObj() {
        schemaService.saveSchema(schema);
        schemaService.newBLock(schema.getUuid());

        assertThrows(NotFoundException.class, () -> schemaService.deleteBlock(schema.getUuid(), RANDOM_UUID));

    }

    @ParameterizedTest
    @DisplayName("save external schema with uuids")
    @MethodSource("provideExternalUuids")
    void saveExternalSchema__returnObj(String externalSchemaUuid) {
        schemaService.saveSchema(schema);

        assertEquals(1, schemaRepository.count());

        Schema externalSchema = Schema.builder()
                .uuid(externalSchemaUuid)
                .name(VALID_STR)
                .description(VALID_STR).build();

        schemaService.saveExternalSchema(externalSchema);

        assertNotNull(externalSchema.getUuid());
        assertEquals(2, schemaRepository.count());
    }

    @TestFactory
    Stream<DynamicTest> dynamicTests() {
        return Stream.of(
                dynamicTest(
                        "find schema by null uuid",
                        () -> assertThrows(DataAccessException.class, () -> schemaService.findBySchemaUuid(null))
                ),

                dynamicTest(
                        "find schema by non existent uuid",
                        () -> assertNull(schemaService.findBySchemaUuid(RANDOM_UUID))
                ),
                dynamicTest(
                        "save null schema",
                        () -> assertThrows(NullPointerException.class, () -> schemaService.saveSchema(null))
                ),
                dynamicTest(
                        "update null schema",
                        () -> assertThrows(NullPointerException.class, () -> schemaService.updateSchema(null))
                ),
                dynamicTest(
                        "delete by null schema uuid",
                        () -> assertThrows(DataAccessException.class, () -> schemaService.deleteSchemaByUuid(null))
                ),
                dynamicTest(
                        "delete by non existent schema uuid",
                        () -> assertThrows(DataAccessException.class, () -> schemaService.deleteSchemaByUuid(RANDOM_UUID))
                ),
                dynamicTest(
                        "delete by non null schema uuid",
                        () -> assertThrows(DataAccessException.class, () -> schemaService.newBLock(null))
                ),
                dynamicTest(
                        "new block for non existent schema",
                        () -> assertThrows(NotFoundException.class, () -> schemaService.newBLock(RANDOM_UUID))
                ),
                dynamicTest(
                        "delete block by existing uuid for null schema",
                        () -> assertThrows(DataAccessException.class, () -> {
                            Block block = blockService.newBlock();
                            schemaService.deleteBlock(null, block.getUuid());
                        })
                ),
                dynamicTest(
                        "delete block by non exisitng uuid for null schema",
                        () -> assertThrows(DataAccessException.class, () -> schemaService.deleteBlock(null, RANDOM_UUID))
                ),
                dynamicTest(
                        "delete block by null uuid for null schema",
                        () -> assertThrows(DataAccessException.class, () -> schemaService.deleteBlock(null, null))
                ),
                dynamicTest(
                        "save null external schema",
                        () -> assertThrows(NullPointerException.class, () -> schemaService.saveExternalSchema(null))
                )
        );
    }


}