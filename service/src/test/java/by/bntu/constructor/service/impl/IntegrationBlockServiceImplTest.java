package by.bntu.constructor.service.impl;

import by.bntu.constructor.domain.*;
import by.bntu.constructor.service.InputService;
import by.bntu.constructor.repository.InputRepository;
import by.bntu.constructor.repository.BlockRepository;
import by.bntu.constructor.repository.OutputRepository;
import by.bntu.constructor.service.BlockService;
import by.bntu.constructor.service.OutputService;
import by.bntu.constructor.service.exception.NotFoundException;
import by.bntu.constructor.service.impl.config.TestConfig;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static by.bntu.constructor.domain.Variable.Type.INPUT;
import static by.bntu.constructor.domain.Variable.Type.OUTPUT;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@SpringJUnitConfig(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class IntegrationBlockServiceImplTest {

    private static final String RANDOM_UUID = UUID.randomUUID().toString();
    private static final String VALID_STR = RandomStringUtils.random(127, true, true);
    private static final double VALID_DOUBLE = RandomUtils.nextDouble();

    @Autowired
    private BlockService blockService;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private OutputRepository outputRepository;

    @Autowired
    private InputRepository inputRepository;

    @Autowired
    private InputService inputService;

    @Autowired
    private OutputService outputService;

    private Block block;

    private static Stream<Arguments> provideExternalUuids() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(UUID.randomUUID().toString(), null),
                Arguments.of(null, UUID.randomUUID().toString())
        );
    }

    private static Stream<Arguments> provideVarsParams() {
        return Stream.of(
                Arguments.of(INPUT, "key", .1),
                Arguments.of(OUTPUT, "key", .1)
        );
    }

    @BeforeEach
    void setUp() {
        block = Block.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(VALID_DOUBLE)
                .positionY(VALID_DOUBLE)
                .style(Style.builder()
                        .inputStyle(VALID_STR)
                        .inputAnchorStyle(VALID_STR)
                        .outputStyle(VALID_STR)
                        .outputAnchorStyle(VALID_STR)
                        .build())
                .build();

    }

    @Test
    @DisplayName("save valid block")
    void saveBlock_validObj_returnObj() {
        assertNull(block.getUuid());
        assertEquals(0, blockRepository.count());
        blockService.saveBlock(block);
        assertNotNull(block.getUuid());
        assertEquals(1, blockRepository.count());
    }

    @Test
    @DisplayName("new block")
    void newBlock__returnObj() {
        assertEquals(0, blockRepository.count());
        Block block = blockService.newBlock();
        assertNotNull(block.getUuid());
        assertEquals(1, blockRepository.count());

    }

    @Test
    @DisplayName("new output for existent block")
    void newOutput__existentUuid__returnObj() {
        blockService.saveBlock(block);
        assertEquals(0, block.getOutputs().size());
        assertEquals(0, outputRepository.count());
        Output output = blockService.newOutput(block.getUuid());
        assertEquals(1, block.getOutputs().size());
        assertNotNull(output.getUuid());
        assertEquals(1, outputRepository.count());
    }

    @Test
    @DisplayName("new input for existent block")
    void newInput__existentUuid__returnObj() {
        blockService.saveBlock(block);
        assertEquals(0, block.getInputs().size());
        assertEquals(0, inputRepository.count());
        Input input = blockService.newInput(block.getUuid());
        assertEquals(1, block.getInputs().size());
        assertNotNull(input.getUuid());
        assertEquals(1, inputRepository.count());
    }


    @Test
    @DisplayName("delete input for existent block")
    void deleteInput__existentUuid__returnObj() {
        block.getInputs().add(Input.builder().build());
        assertEquals(1, block.getInputs().size());
        blockService.saveBlock(block);
        assertEquals(1, inputRepository.count());

        blockService.deleteInput(block.getUuid(), block.getInputs().get(0).getUuid());
        assertEquals(0, block.getInputs().size());
    }

    @Test
    @DisplayName("delete non existent input for existent block")
    void deleteInput__nonExistentInputUuid__returnObj() {
        blockService.saveBlock(block);
        assertThrows(NotFoundException.class, () -> blockService.deleteInput(block.getUuid(), RANDOM_UUID));
    }

    @Test
    @DisplayName("delete non existent output for existent block")
    void deleteOutput__nonExistentOutputUuid__returnObj() {
        blockService.saveBlock(block);
        assertThrows(NotFoundException.class, () -> blockService.deleteOutput(block.getUuid(), RANDOM_UUID));
    }

    @Test
    @DisplayName("delete null input for existent block")
    void deleteInput__nullInputUuid__returnObj() {
        blockService.saveBlock(block);
        assertThrows(DataAccessException.class, () -> blockService.deleteInput(block.getUuid(), null));
    }

    @Test
    @DisplayName("delete null output for existent block")
    void deleteOutput__nullOutputUuid__returnObj() {
        blockService.saveBlock(block);
        assertThrows(DataAccessException.class, () -> blockService.deleteOutput(block.getUuid(), null));
    }


    @Test
    @DisplayName("delete output for existent block")
    void deleteOutput__existentUuid__returnObj() {
        block.getOutputs().add(Output.builder().build());
        assertEquals(1, block.getOutputs().size());
        blockService.saveBlock(block);
        assertEquals(1, outputRepository.count());

        blockService.deleteOutput(block.getUuid(), block.getOutputs().get(0).getUuid());
        assertEquals(0, block.getOutputs().size());
    }


    @Test
    @DisplayName("delete existent output for not allowing existent block")
    void deleteOutput__existentUuidAndNotAllowingBlock__return() {
        block.getOutputs().add(Output.builder().build());

        assertEquals(1, block.getOutputs().size());

        Output output = outputService.newOutput();
        blockService.saveBlock(block);

        assertEquals(2, outputRepository.count());

        assertThrows(IllegalArgumentException.class, () -> blockService.deleteOutput(block.getUuid(), output.getUuid()));

        assertEquals(1, block.getOutputs().size());
        assertEquals(2, outputRepository.count());
    }


    @Test
    @DisplayName("delete existent input for not allowing existent block")
    void deleteInput__existentUuidAndNotAllowingBlock__return() {
        block.getInputs().add(Input.builder().build());

        assertEquals(1, block.getInputs().size());

        Input input = inputService.newInput();
        blockService.saveBlock(block);

        assertEquals(2, inputRepository.count());
        assertThrows(NotFoundException.class, () -> blockService.deleteInput(block.getUuid(), input.getUuid()));

        assertEquals(1, block.getInputs().size());
        assertEquals(2, inputRepository.count());
    }

    @ParameterizedTest
    @DisplayName("save external block with uuids")
    @MethodSource("provideExternalUuids")
    void saveExternalBlocks__returnObj(String externalBlockUuid, String externalBlockStyleUuid) {

        blockService.saveBlock(block);

        assertEquals(1, blockRepository.count());

        Block externalBlock = Block.builder()
                .uuid(externalBlockUuid)
                .name(VALID_STR)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(VALID_DOUBLE)
                .positionY(VALID_DOUBLE)
                .style(Style.builder()
                        .uuid(externalBlockStyleUuid)
                        .inputStyle(VALID_STR)
                        .inputAnchorStyle(VALID_STR)
                        .outputStyle(VALID_STR)
                        .outputAnchorStyle(VALID_STR)
                        .build())
                .build();
        blockService.saveExternalBlocks(Collections.singletonList(externalBlock));

        assertNotNull(externalBlock.getUuid());
        assertEquals(2, blockRepository.count());
    }

    @ParameterizedTest
    @DisplayName("put valid vars values")
    @MethodSource("provideVarsParams")
    void putVarsValue_validValues_returnObj(Variable.Type type, String param, Double value) {
        List<Variable> vars = type == Variable.Type.INPUT ? block.getInputVars() : block.getOutputVars();
        blockService.saveBlock(block);
        assertTrue(vars.isEmpty());
        Variable expectedVariable = Variable.builder()
                .param(param)
                .type(type)
                .value(value).build();

        blockService.putVar(block.getUuid(), expectedVariable);
        assertEquals(1, vars.size());
        assertEquals(expectedVariable, vars.get(0));

    }

    @Test
    @DisplayName("put vars values")
    void findByBlockUuid__existentUuid_returnObj() {
        Block expected = blockService.saveBlock(block);
        Block actual = blockService.findByBlockUuid(expected.getUuid());
        assertEquals(expected, actual);
    }


    @TestFactory
    Stream<DynamicTest> dynamicTests() {
        return Stream.of(
                dynamicTest(
                        "delete null output for null block uuid", () -> assertThrows(DataAccessException.class, () -> blockService.deleteOutput(null, null))
                ),
                dynamicTest(
                        "delete null input for null block uuid", () -> assertThrows(DataAccessException.class, () -> blockService.deleteInput(null, null))
                ),
                dynamicTest(
                        "delete output for null block uuid", () -> assertThrows(DataAccessException.class, () -> blockService.deleteOutput(null, RANDOM_UUID))
                ),
                dynamicTest(
                        "delete input for null block uuid", () -> assertThrows(DataAccessException.class, () -> blockService.deleteInput(null, RANDOM_UUID))
                ),
                dynamicTest(
                        "delete output for non existent block uuid", () -> assertThrows(NotFoundException.class, () -> blockService.deleteOutput(RANDOM_UUID, RANDOM_UUID))
                ),
                dynamicTest(
                        "delete input for non existent block uuid", () -> assertThrows(NotFoundException.class, () -> blockService.deleteInput(RANDOM_UUID, RANDOM_UUID))
                ),
                dynamicTest(
                        "new output for null block uuid", () -> assertThrows(DataAccessException.class, () -> blockService.newOutput(null))
                ),
                dynamicTest(
                        "new output for non existent block uuid", () -> assertThrows(NotFoundException.class, () -> blockService.newOutput(RANDOM_UUID))
                ),
                dynamicTest(
                        "new input for null block uuid", () -> assertThrows(DataAccessException.class, () -> blockService.newInput(null))
                ),
                dynamicTest(
                        "new input for non existent block uuid", () -> assertThrows(NotFoundException.class, () -> blockService.newInput(RANDOM_UUID))
                ),
                dynamicTest(
                        "find by non existent uuid", () -> assertNull(blockService.findByBlockUuid(RANDOM_UUID))
                ),
                dynamicTest(
                        "find by null uuid",
                        () -> assertThrows(DataAccessException.class, () -> blockService.findByBlockUuid(null))
                ),
                dynamicTest(
                        "save null blocks",
                        () -> assertThrows(NullPointerException.class, () -> blockService.saveAllBlocks(null))
                ),
                dynamicTest(
                        "save null external blocks",
                        () -> assertThrows(NullPointerException.class, () -> blockService.saveExternalBlocks(null))
                ),
                dynamicTest(
                        "save null block",
                        () -> assertThrows(NullPointerException.class, () -> blockService.saveBlock(null))
                )
        );
    }
}