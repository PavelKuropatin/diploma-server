package by.bntu.constructor.service.impl;

import by.bntu.constructor.domain.Input;
import by.bntu.constructor.repository.InputRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitInputServiceImplTest {

    private static final long TEST_SIZE = 3;
    private static final String RANDOM_UUID = UUID.randomUUID().toString();

    private final Answer<Input> setInputUuid = invocation -> {
        Input input = invocation.getArgument(0);
        if (input == null) {
            throw new NullPointerException();
        }
        input.setUuid(UUID.randomUUID().toString());
        return input;
    };

    private final Answer<List<Input>> setInputsUuid = invocation -> {
        List<Input> inputs = invocation.getArgument(0);
        if (inputs.contains(null)) {
            throw new NullPointerException();
        }
        inputs.forEach(connection -> connection.setUuid(UUID.randomUUID().toString()));
        return inputs;
    };

    @InjectMocks
    private InputServiceImpl inputService;

    @Mock
    private InputRepository inputRepository;

    @Test
    @DisplayName("save valid input")
    void saveInput_validInput_returnObj() {
        when(inputRepository.save(any(Input.class))).thenAnswer(setInputUuid);
        when(inputRepository.count()).thenReturn(0L, 1L);
        Input input = Input.builder()
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
        when(inputRepository.saveAll(anyList())).thenAnswer(setInputsUuid);
        when(inputRepository.count()).thenReturn(0L, TEST_SIZE);

        List<Input> inputs = LongStream.range(0, TEST_SIZE)
                .boxed()
                .map(output -> Input.builder().build())
                .collect(Collectors.toList());

        assertEquals(0, inputRepository.count());

        inputService.saveAllInputs(inputs);

        List<String> actualUuids = inputs.stream().map(Input::getUuid).sorted().collect(Collectors.toList());
        for (String uuid : actualUuids) {
            assertNotNull(uuid);
        }
        assertEquals(TEST_SIZE, inputRepository.count());
    }


    @Test
    @DisplayName("new input")
    void newInput__returnObj() {
        when(inputRepository.save(any(Input.class))).thenAnswer(setInputUuid);
        when(inputRepository.count()).thenReturn(0L, 1L);

        assertEquals(0, inputRepository.count());

        Input input = inputService.newInput();

        assertNotNull(input.getUuid());
        assertEquals(1, inputRepository.count());
    }

    @Test
    @DisplayName("find by valid uuid")
    void findByInputUuid_validUUID_returnObj() {
        Input expected = Input.builder()
                .build();
        when(inputRepository.save(any(Input.class))).thenAnswer(setInputUuid);
        when(inputRepository.findById(any(String.class))).thenReturn(Optional.of(expected));

        inputService.saveInput(expected);

        assertNotNull(expected.getUuid());

        Input actual = inputService.findByInputUuid(expected.getUuid());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("find by null uuid")
    void findByInputUuid_nullUUID_returnObj() {
        when(inputRepository.findById(nullable(String.class))).thenReturn(Optional.empty());

        Input actual = inputService.findByInputUuid(null);

        assertNull(actual);
    }


    @Test
    @DisplayName("find by non existent uuid")
    void findByInputUuid_nonExistentUuid_returnObj() {
        when(inputRepository.findById(any(String.class))).thenReturn(Optional.empty());

        Input actual = inputService.findByInputUuid(RANDOM_UUID);

        assertNull(actual);
    }

}