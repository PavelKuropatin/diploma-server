package by.bntu.constructor.service.impl;

import by.bntu.constructor.domain.Input;
import by.bntu.constructor.repository.InputRepository;
import by.bntu.constructor.service.InputService;
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
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@SpringJUnitConfig(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class IntegrationInputServiceImplTest {

    private static final int TEST_SIZE = 3;
    private static final String RANDOM_UUID = UUID.randomUUID().toString();

    @Autowired
    private InputService inputService;

    @Autowired
    private InputRepository inputRepository;


    @Test
    @DisplayName("save valid input")
    void saveInput() {
        Input input = Input.builder()
//                .connections(Collections.emptyList())
                .build();
        assertNull(input.getUuid());
        assertEquals(0, inputRepository.count());
        inputService.saveInput(input);
        assertNotNull(input.getUuid());
        assertEquals(1, inputRepository.count());
    }

    @Test
    @DisplayName("save valid inputs")
    void saveAllInputs_validInputs_returnObj() {
        List<Input> inputs = LongStream.range(0, TEST_SIZE)
                .boxed()
                .map(output -> Input.builder().build())
                .collect(Collectors.toList());

        inputService.saveAllInputs(inputs);

        List<String> actualUuids = inputs.stream().map(Input::getUuid).sorted().collect(Collectors.toList());
        for (String uuid : actualUuids) {
            assertNotNull(uuid);
        }
        assertEquals(TEST_SIZE, inputRepository.count());
    }

    @Test
    @DisplayName("save invalid inputs")
    void saveAllInputs_containsInvalidInput_returnObj() {
        List<Input> inputs = LongStream.range(0, TEST_SIZE)
                .boxed()
                .map(output -> Input.builder().build())
                .collect(Collectors.toList());

        inputs.set(RandomUtils.nextInt(0, inputs.size()), null);

        assertThrows(NullPointerException.class, () -> inputService.saveAllInputs(inputs));

    }

    @Test
    @DisplayName("new input")
    void newInput() {
        assertEquals(0, inputRepository.count());
        Input input = inputService.newInput();
        assertNotNull(input.getUuid());
        assertEquals(1, inputRepository.count());
    }

    @Test
    @DisplayName("find by valid uuid")
    void findByInputUuid_validUUID_returnObj() {
        Input expected = Input.builder()
//                .connections(Collections.emptyList())
                .build();
        inputService.saveInput(expected);

        assertNotNull(expected.getUuid());

        Input actual = inputService.findByInputUuid(expected.getUuid());

        assertEquals(expected, actual);
    }

    @TestFactory
    Stream<DynamicTest> dynamicTests() {
        return Stream.of(
                dynamicTest(
                        "find by non exist uuid", () -> assertNull(inputService.findByInputUuid(RANDOM_UUID))
                ),
                dynamicTest(
                        "find by null uuid",
                        () -> assertThrows(DataAccessException.class, () -> inputService.findByInputUuid(null))
                ),
                dynamicTest(
                        "save null inputs",
                        () -> assertThrows(NullPointerException.class, () -> inputService.saveAllInputs(null))
                ),
//                dynamicTest(
//                        "save invalid input",
//                        () -> assertThrows(NullPointerException.class, () -> inputService.saveInput(Input.builder().connections(null).build()))
//                ),
                dynamicTest(
                        "save null input",
                        () -> assertThrows(DataAccessException.class, () -> inputService.saveInput(null))
                )
        );
    }
}