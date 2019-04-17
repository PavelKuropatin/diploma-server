package by.bntu.diploma.diagram.service.impl.integration;

import by.bntu.diploma.diagram.domain.*;
import by.bntu.diploma.diagram.repository.SourceRepository;
import by.bntu.diploma.diagram.repository.StateRepository;
import by.bntu.diploma.diagram.repository.TargetRepository;
import by.bntu.diploma.diagram.service.SourceService;
import by.bntu.diploma.diagram.service.StateService;
import by.bntu.diploma.diagram.service.TargetService;
import by.bntu.diploma.diagram.service.exception.NotFoundException;
import by.bntu.diploma.diagram.service.impl.config.TestConfig;
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
import java.util.Map;
import java.util.stream.Stream;

import static by.bntu.diploma.diagram.domain.ContainerType.INPUT;
import static by.bntu.diploma.diagram.domain.ContainerType.OUTPUT;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@SpringJUnitConfig(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class IntegrationStateServiceImplTest {

    private static final String VALID_STR = RandomStringUtils.random(127, true, true);
    private static final int VALID_INT = RandomUtils.nextInt();

    @Autowired
    private StateService stateService;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private TargetRepository targetRepository;

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private SourceService sourceService;

    @Autowired
    private TargetService targetService;

    private State state;

    private static Stream<Arguments> provideExternalUuids() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(1L, 1L),
                Arguments.of(3L, 3L)
        );
    }

    private static Stream<Arguments> provideContainerParams() {
        return Stream.of(
                Arguments.of(INPUT, "key", .1),
                Arguments.of(OUTPUT, "key", .1)
        );
    }

    @BeforeEach
    void setUp() {
        state = State.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .style(Style.builder()
                        .sourceStyle(VALID_STR)
                        .sourceAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .build())
                .build();

    }

    @Test
    @DisplayName("save valid state")
    void saveState_validObj_returnObj() {
        assertNull(state.getUuid());
        assertEquals(0, stateRepository.count());
        stateService.saveState(state);
        assertEquals(1L, (long) state.getUuid());
        assertEquals(1, stateRepository.count());
    }

    @Test
    @DisplayName("new state")
    void newState__returnObj() {
        assertEquals(0, stateRepository.count());
        State state = stateService.newState();
        assertEquals(1L, (long) state.getUuid());
        assertEquals(1, stateRepository.count());

    }

    @Test
    @DisplayName("new target for existent state")
    void newTarget__existentUuid__returnObj() {
        stateService.saveState(state);
        assertEquals(0, state.getTargets().size());
        assertEquals(0, targetRepository.count());
        Target target = stateService.newTarget(state.getUuid());
        assertEquals(1, state.getTargets().size());
        assertEquals(1L, (long) target.getUuid());
        assertEquals(1, targetRepository.count());
    }

    @Test
    @DisplayName("new source for existent state")
    void newSource__existentUuid__returnObj() {
        stateService.saveState(state);
        assertEquals(0, state.getSources().size());
        assertEquals(0, sourceRepository.count());
        Source source = stateService.newSource(state.getUuid());
        assertEquals(1, state.getSources().size());
        assertEquals(1L, (long) source.getUuid());
        assertEquals(1, sourceRepository.count());
    }

    @Test
    @DisplayName("delete source for existent state")
    void deleteSource__existentUuid__returnObj() {
        state.getSources().add(Source.builder().build());
        assertEquals(1, state.getSources().size());
        stateService.saveState(state);
        assertEquals(1, sourceRepository.count());

        stateService.deleteSource(state.getUuid(), state.getSources().get(0).getUuid());
        assertEquals(0, state.getSources().size());
    }

    @Test
    @DisplayName("delete non existent source for existent state")
    void deleteSource__nonExistentSourceUuid__returnObj() {
        stateService.saveState(state);
        assertThrows(NotFoundException.class, () -> stateService.deleteSource(state.getUuid(), 2L));
    }

    @Test
    @DisplayName("delete non existent target for existent state")
    void deleteTarget__nonExistentTargetUuid__returnObj() {
        stateService.saveState(state);
        assertThrows(NotFoundException.class, () -> stateService.deleteTarget(state.getUuid(), 2L));
    }

    @Test
    @DisplayName("delete null source for existent state")
    void deleteSource__nullSourceUuid__returnObj() {
        stateService.saveState(state);
        assertThrows(DataAccessException.class, () -> stateService.deleteSource(state.getUuid(), null));
    }

    @Test
    @DisplayName("delete null target for existent state")
    void deleteTarget__nullTargetUuid__returnObj() {
        stateService.saveState(state);
        assertThrows(DataAccessException.class, () -> stateService.deleteTarget(state.getUuid(), null));
    }

    @Test
    @DisplayName("delete target for existent state")
    void deleteTarget__existentUuid__returnObj() {
        state.getTargets().add(Target.builder().build());
        assertEquals(1, state.getTargets().size());
        stateService.saveState(state);
        assertEquals(1, targetRepository.count());

        stateService.deleteTarget(state.getUuid(), state.getTargets().get(0).getUuid());
        assertEquals(0, state.getTargets().size());
    }

    @Test
    @DisplayName("delete existent target for not allowing existent state")
    void deleteTarget__existentUuidAndNotAllowingState__return() {
        state.getTargets().add(Target.builder().build());

        assertEquals(1, state.getTargets().size());

        Target target = targetService.newTarget();
        stateService.saveState(state);

        assertEquals(2, targetRepository.count());

        stateService.deleteTarget(state.getUuid(), target.getUuid());
        assertEquals(1, state.getTargets().size());
        assertEquals(2, targetRepository.count());
    }

    @Test
    @DisplayName("delete existent source for not allowing existent state")
    void deleteSource__existentUuidAndNotAllowingState__return() {
        state.getSources().add(Source.builder().build());

        assertEquals(1, state.getSources().size());

        Source source = sourceService.newSource();
        stateService.saveState(state);

        assertEquals(2, sourceRepository.count());

        stateService.deleteSource(state.getUuid(), source.getUuid());
        assertEquals(1, state.getSources().size());
        assertEquals(2, sourceRepository.count());
    }

    @ParameterizedTest
    @DisplayName("save external state with uuids")
    @MethodSource("provideExternalUuids")
    void saveExternalStates__returnObj(Long externalStateUuid, Long externalStateStyleUuid) {

        stateService.saveState(state);

        assertEquals(1, stateRepository.count());

        State externalState = State.builder()
                .uuid(externalStateUuid)
                .name(VALID_STR)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .style(Style.builder()
                        .uuid(externalStateStyleUuid)
                        .sourceStyle(VALID_STR)
                        .sourceAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .build())
                .build();
        stateService.saveExternalStates(Collections.singletonList(externalState));

        assertEquals(2, (long) externalState.getUuid());
        assertEquals(2, stateRepository.count());
    }

    @ParameterizedTest
    @DisplayName("put valid container values")
    @MethodSource("provideContainerParams")
    void putContainerValue_validValues_returnObj(ContainerType type, String param, Double value) {
        Map<String, Double> container = type == ContainerType.INPUT ? state.getInputContainer() : state.getOutputContainer();
        stateService.saveState(state);
        assertTrue(container.isEmpty());
        stateService.putContainerValue(state.getUuid(), type, param, value);
        assertEquals(1, container.size());
        assertEquals(container.get(param), value);

    }

    @Test
    @DisplayName("put container values")
    void findByStateUuid__existentUuid_returnObj() {
        State expected = stateService.saveState(state);
        State actual = stateService.findByStateUUID(expected.getUuid());
        assertEquals(expected, actual);
    }


    @TestFactory
    Stream<DynamicTest> dynamicTests() {
        return Stream.of(
                dynamicTest(
                        "delete null target for null state uuid", () -> assertThrows(DataAccessException.class, () -> stateService.deleteTarget(null, null))
                ),
                dynamicTest(
                        "delete null source for null state uuid", () -> assertThrows(DataAccessException.class, () -> stateService.deleteSource(null, null))
                ),
                dynamicTest(
                        "delete target for null state uuid", () -> assertThrows(DataAccessException.class, () -> stateService.deleteTarget(null, 1L))
                ),
                dynamicTest(
                        "delete source for null state uuid", () -> assertThrows(DataAccessException.class, () -> stateService.deleteSource(null, 1L))
                ),
                dynamicTest(
                        "delete target for non existent state uuid", () -> assertThrows(NotFoundException.class, () -> stateService.deleteTarget(3L, 1L))
                ),
                dynamicTest(
                        "delete source for non existent state uuid", () -> assertThrows(NotFoundException.class, () -> stateService.deleteSource(3L, 1L))
                ),
                dynamicTest(
                        "new target for null state uuid", () -> assertThrows(DataAccessException.class, () -> stateService.newTarget(null))
                ),
                dynamicTest(
                        "new target for non existent state uuid", () -> assertThrows(NotFoundException.class, () -> stateService.newTarget(3L))
                ),
                dynamicTest(
                        "new source for null state uuid", () -> assertThrows(DataAccessException.class, () -> stateService.newSource(null))
                ),
                dynamicTest(
                        "new source for non existent state uuid", () -> assertThrows(NotFoundException.class, () -> stateService.newSource(3L))
                ),
                dynamicTest(
                        "find by non existent uuid", () -> assertNull(stateService.findByStateUUID(3L))
                ),
                dynamicTest(
                        "find by null uuid",
                        () -> assertThrows(DataAccessException.class, () -> stateService.findByStateUUID(null))
                ),
                dynamicTest(
                        "save null states",
                        () -> assertThrows(NullPointerException.class, () -> stateService.saveAllStates(null))
                ),
                dynamicTest(
                        "save null external states",
                        () -> assertThrows(NullPointerException.class, () -> stateService.saveExternalStates(null))
                ),
                dynamicTest(
                        "save null state",
                        () -> assertThrows(NullPointerException.class, () -> stateService.saveState(null))
                )
        );
    }
}