package by.bntu.constructor.service.impl;

import by.bntu.constructor.domain.Output;
import by.bntu.constructor.repository.OutputRepository;
import by.bntu.constructor.service.OutputService;
import by.bntu.constructor.service.impl.config.TestConfig;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@SpringJUnitConfig(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class IntegrationOutputServiceImplTest {

    private static final int TEST_SIZE = 3;
    private static final String RANDOM_UUID = UUID.randomUUID().toString();

    @Autowired
    private OutputService outputService;

    @Autowired
    private OutputRepository outputRepository;

    @Test
    @DisplayName("save valid output")
    void saveOutput_validObj_returnObj() {
        Output output = Output.builder().build();
        assertNull(output.getUuid());
        outputService.saveOutput(output);
        assertNotNull(output.getUuid());
        assertEquals(1, outputRepository.count());
    }

    @Test
    @DisplayName("save valid outputs")
    void saveAllOutputs_validOutput_returnObjs() {
        List<Output> outputs = IntStream.range(0, TEST_SIZE)
                .boxed()
                .map(i -> Output.builder().build())
                .collect(Collectors.toList());
        outputService.saveAllOutputs(outputs);

        List<String> actualUuids = outputs.stream().map(Output::getUuid).sorted().collect(Collectors.toList());
        for (String uuid : actualUuids) {
            assertNotNull(uuid);
        }
        assertEquals(TEST_SIZE, outputRepository.count());
    }

    @Test
    @DisplayName("save invalid outputs")
    void saveAllConnections_containsInvalidConnection_returnException() {
        List<Output> outputs = IntStream.range(0, TEST_SIZE)
                .boxed()
                .map(i -> Output.builder().build())
                .collect(Collectors.toList());

        outputs.set(RandomUtils.nextInt(0, outputs.size()), null);
        assertThrows(NullPointerException.class, () -> {
            outputService.saveAllOutputs(outputs);
        }, "Output object must be not null");

    }

    @Test
    @DisplayName("new output")
    void newOutput__returnObj() {
        Output output = outputService.newOutput();
        assertNotNull(output.getUuid());
        assertEquals(1, outputRepository.count());
    }

    @Test
    @DisplayName("find by valid uuid")
    void findByOutputUuid_validUUID_returnObj() {
        Output expected = Output.builder().build();
        outputService.saveOutput(expected);
        assertNotNull(expected.getUuid());
        Output actual = outputService.findByOutputUuid(expected.getUuid());
        assertEquals(expected, actual);
    }

    @TestFactory
    Stream<DynamicTest> dynamicTests() {
        return Stream.of(
                dynamicTest(
                        "find by non exist uuid", () -> assertNull(outputService.findByOutputUuid(RANDOM_UUID))
                ),
                dynamicTest(
                        "find by null uuid",
                        () -> assertThrows(DataAccessException.class, () -> outputService.findByOutputUuid(null))
                ),
                dynamicTest(
                        "save null output",
                        () -> assertThrows(DataAccessException.class, () -> outputService.saveOutput(null))
                )
        );
    }

}