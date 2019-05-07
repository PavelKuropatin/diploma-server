package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.domain.Target;
import by.bntu.diploma.diagram.repository.TargetRepository;
import by.bntu.diploma.diagram.service.TargetService;
import by.bntu.diploma.diagram.service.impl.config.TestConfig;
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
class IntegrationTargetServiceImplTest {

    private static final int TEST_SIZE = 3;
    private static final String RANDOM_UUID = UUID.randomUUID().toString();

    @Autowired
    private TargetService targetService;

    @Autowired
    private TargetRepository targetRepository;

    @Test
    @DisplayName("save valid target")
    void saveTarget_validObj_returnObj() {
        Target target = Target.builder().build();
        assertNull(target.getUuid());
        targetService.saveTarget(target);
        assertNotNull(target.getUuid());
        assertEquals(1, targetRepository.count());
    }

    @Test
    @DisplayName("save valid targets")
    void saveAllTargets_validTarget_returnObjs() {
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
        List<Target> targets = IntStream.range(0, TEST_SIZE)
                .boxed()
                .map(i -> Target.builder().build())
                .collect(Collectors.toList());

        targets.set(RandomUtils.nextInt(0, targets.size()), null);
        assertThrows(DataAccessException.class, () -> {
            targetService.saveAllTargets(targets);
        }, "Target object must be not null");

    }

    @Test
    @DisplayName("new target")
    void newTarget__returnObj() {
        Target target = targetService.newTarget();
        assertNotNull(target.getUuid());
        assertEquals(1, targetRepository.count());
    }

    @Test
    @DisplayName("find by valid uuid")
    void findByTargetUuid_validUUID_returnObj() {
        Target expected = Target.builder().build();
        targetService.saveTarget(expected);
        assertNotNull(expected.getUuid());
        Target actual = targetService.findByTargetUuid(expected.getUuid());
        assertEquals(expected, actual);
    }

    @TestFactory
    Stream<DynamicTest> dynamicTests() {
        return Stream.of(
                dynamicTest(
                        "find by non exist uuid", () -> assertNull(targetService.findByTargetUuid(RANDOM_UUID))
                ),
                dynamicTest(
                        "find by null uuid",
                        () -> assertThrows(DataAccessException.class, () -> targetService.findByTargetUuid(null))
                ),
                dynamicTest(
                        "save null target",
                        () -> assertThrows(DataAccessException.class, () -> targetService.saveTarget(null))
                )
        );
    }

}