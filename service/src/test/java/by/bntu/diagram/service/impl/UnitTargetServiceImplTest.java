package by.bntu.diagram.service.impl;

import by.bntu.diagram.domain.Target;
import by.bntu.diagram.repository.TargetRepository;
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
class UnitTargetServiceImplTest {

    private static final int TEST_SIZE = 3;

    private final Answer<Target> setTargetUuid = invocation -> {
        Target target = invocation.getArgument(0);
        if (target == null) {
            throw new NullPointerException();
        }
        target.setUuid(UUID.randomUUID().toString());
        return target;
    };

    private final Answer<List<Target>> setTargetsUuid = invocation -> {
        List<Target> targets = invocation.getArgument(0);
        if (targets.contains(null)) {
            throw new NullPointerException();
        }
        targets.forEach(target -> target.setUuid(UUID.randomUUID().toString()));
        return targets;
    };

    @InjectMocks
    private TargetServiceImpl targetService;

    @Mock
    private TargetRepository targetRepository;

    private static Stream<Arguments> provideInvalidUuids() {
        return Stream.of(
                Arguments.of(UUID.randomUUID()),
                Arguments.of((UUID) null)
        );
    }


    @Test
    @DisplayName("save valid target")
    void saveTarget_validObj_returnObj() {
        when(targetRepository.count()).thenReturn(0L, 1L);
        when(targetRepository.save(any(Target.class))).thenAnswer(setTargetUuid);
        Target target = Target.builder().build();

        assertNull(target.getUuid());
        assertEquals(0, targetRepository.count());

        targetService.saveTarget(target);

        assertNotNull(target.getUuid());
        assertEquals(1, targetRepository.count());
    }

    @Test
    @DisplayName("save null target")
    void saveTarget_nullObj_returnObj() {
        when(targetRepository.count()).thenReturn(0L);
        when(targetRepository.save(nullable(Target.class))).thenAnswer(setTargetUuid);

        assertEquals(0, targetRepository.count());
        assertThrows(NullPointerException.class, () -> targetService.saveTarget(null));
        assertEquals(0, targetRepository.count());
    }

    @Test
    @DisplayName("save valid targets")
    void saveAllTargets_validTarget_returnObjs() {
        when(targetRepository.saveAll(anyList())).thenAnswer(setTargetsUuid);
        when(targetRepository.count()).thenReturn(0L, 3L);


        assertEquals(0, targetRepository.count());

        List<Target> targets = IntStream.range(0, TEST_SIZE)
                .boxed()
                .map(i -> Target.builder().build())
                .collect(Collectors.toList());

        targetService.saveAllTargets(targets);

        List<String> actualUuids = targets.stream().map(Target::getUuid).sorted().collect(Collectors.toList());
        for (String uuid : actualUuids) {
            assertNotNull(uuid);
        }
        assertEquals(TEST_SIZE, targetRepository.count());
    }

    @Test
    @DisplayName("save invalid targets")
    void saveAllConnections_containsInvalidConnection_returnException() {
        when(targetRepository.count()).thenReturn(0L);

        List<Target> targets = IntStream.range(0, TEST_SIZE)
                .boxed()
                .map(i -> Target.builder().build())
                .collect(Collectors.toList());

        targets.set(RandomUtils.nextInt(0, targets.size()), null);

        assertEquals(0, targetRepository.count());
        assertThrows(NullPointerException.class, () -> targetService.saveAllTargets(targets));
        assertEquals(0, targetRepository.count());

    }

    @Test
    @DisplayName("new target")
    void newTarget__returnObj() {
        when(targetRepository.count()).thenReturn(0L, 1L);
        when(targetRepository.save(any(Target.class))).thenAnswer(setTargetUuid);

        assertEquals(0, targetRepository.count());
        Target target = targetService.newTarget();
        assertNotNull(target.getUuid());
        assertEquals(1, targetRepository.count());
    }

    @Test
    @DisplayName("find by valid uuid")
    void findByTargetUuid_validUUID_returnObj() {
        Target expected = Target.builder().build();

        when(targetRepository.save(any(Target.class))).thenAnswer(setTargetUuid);
        when(targetRepository.findById(any(String.class))).thenReturn(Optional.of(expected));

        targetService.saveTarget(expected);

        Target actual = targetService.findByTargetUuid(expected.getUuid());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("find by valid uuid")
    void findByTargetUuid_nonExistentUuid_returnException() {

        when(targetRepository.findById(any(String.class))).thenReturn(Optional.empty());
        Target actual = targetService.findByTargetUuid(UUID.randomUUID().toString());
        assertNull(actual);

    }

    @Test
    @DisplayName("find by invalid uuid")
    void findByTargetUuid_nullUuid_returnException() {

        when(targetRepository.findById(nullable(String.class))).thenReturn(Optional.empty());
        Target actual = targetService.findByTargetUuid(null);
        assertNull(actual);

    }

}