package by.bntu.constructor.service.impl;

import by.bntu.constructor.domain.Output;
import by.bntu.constructor.repository.OutputRepository;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitOutputServiceImplTest {

    private static final int TEST_SIZE = 3;

    private final Answer<Output> setOutputUuid = invocation -> {
        Output output = invocation.getArgument(0);
        if (output == null) {
            throw new NullPointerException();
        }
        output.setUuid(UUID.randomUUID().toString());
        return output;
    };

    private final Answer<List<Output>> setOutputsUuid = invocation -> {
        List<Output> outputs = invocation.getArgument(0);
        if (outputs.contains(null)) {
            throw new NullPointerException();
        }
        outputs.forEach(output -> output.setUuid(UUID.randomUUID().toString()));
        return outputs;
    };

    @InjectMocks
    private OutputServiceImpl outputService;

    @Mock
    private OutputRepository outputRepository;

    private static Stream<Arguments> provideInvalidUuids() {
        return Stream.of(
                Arguments.of(UUID.randomUUID()),
                Arguments.of((UUID) null)
        );
    }


    @Test
    @DisplayName("save valid output")
    void saveOutput_validObj_returnObj() {
        when(outputRepository.count()).thenReturn(0L, 1L);
        when(outputRepository.save(any(Output.class))).thenAnswer(setOutputUuid);
        Output output = Output.builder().build();

        assertNull(output.getUuid());
        assertEquals(0, outputRepository.count());

        outputService.saveOutput(output);

        assertNotNull(output.getUuid());
        assertEquals(1, outputRepository.count());
    }

    @Test
    @DisplayName("save null output")
    void saveOutput_nullObj_returnObj() {
        when(outputRepository.count()).thenReturn(0L);
        when(outputRepository.save(nullable(Output.class))).thenAnswer(setOutputUuid);

        assertEquals(0, outputRepository.count());
        assertThrows(NullPointerException.class, () -> outputService.saveOutput(null));
        assertEquals(0, outputRepository.count());
    }

    @Test
    @DisplayName("save valid outputs")
    void saveAllOutputs_validOutput_returnObjs() {
        when(outputRepository.saveAll(anyList())).thenAnswer(setOutputsUuid);
        when(outputRepository.count()).thenReturn(0L, 3L);


        assertEquals(0, outputRepository.count());

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
        when(outputRepository.count()).thenReturn(0L);

        List<Output> outputs = IntStream.range(0, TEST_SIZE)
                .boxed()
                .map(i -> Output.builder().build())
                .collect(Collectors.toList());

        outputs.set(RandomUtils.nextInt(0, outputs.size()), null);

        assertEquals(0, outputRepository.count());
        assertThrows(NullPointerException.class, () -> outputService.saveAllOutputs(outputs));
        assertEquals(0, outputRepository.count());

    }

    @Test
    @DisplayName("new output")
    void newOutput__returnObj() {
        when(outputRepository.count()).thenReturn(0L, 1L);
        when(outputRepository.save(any(Output.class))).thenAnswer(setOutputUuid);

        assertEquals(0, outputRepository.count());
        Output output = outputService.newOutput();
        assertNotNull(output.getUuid());
        assertEquals(1, outputRepository.count());
    }

    @Test
    @DisplayName("find by valid uuid")
    void findByOutputUuid_validUUID_returnObj() {
        Output expected = Output.builder().build();

        when(outputRepository.save(any(Output.class))).thenAnswer(setOutputUuid);
        when(outputRepository.findById(any(String.class))).thenReturn(Optional.of(expected));

        outputService.saveOutput(expected);

        Output actual = outputService.findByOutputUuid(expected.getUuid());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("find by valid uuid")
    void findByOutputUuid_nonExistentUuid_returnException() {

        when(outputRepository.findById(any(String.class))).thenReturn(Optional.empty());
        Output actual = outputService.findByOutputUuid(UUID.randomUUID().toString());
        assertNull(actual);

    }

    @Test
    @DisplayName("find by invalid uuid")
    void findByOutputUuid_nullUuid_returnException() {

        when(outputRepository.findById(nullable(String.class))).thenReturn(Optional.empty());
        Output actual = outputService.findByOutputUuid(null);
        assertNull(actual);

    }

}