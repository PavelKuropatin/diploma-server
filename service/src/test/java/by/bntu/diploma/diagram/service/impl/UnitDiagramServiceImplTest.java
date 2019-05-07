package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.domain.Diagram;
import by.bntu.diploma.diagram.domain.State;
import by.bntu.diploma.diagram.repository.DiagramRepository;
import by.bntu.diploma.diagram.repository.StateRepository;
import by.bntu.diploma.diagram.service.DiagramService;
import by.bntu.diploma.diagram.service.StateService;
import by.bntu.diploma.diagram.service.exception.NotFoundException;
import by.bntu.diploma.diagram.service.impl.config.TestConfig;
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
class UnitDiagramServiceImplTest {

    private static final String VALID_STR = RandomStringUtils.random(127, true, true);
    private static final String RANDOM_UUID = UUID.randomUUID().toString();

    @Autowired
    private DiagramService diagramService;

    @Autowired
    private DiagramRepository diagramRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private StateService stateService;

    private Diagram diagram;

    private static Stream<Arguments> provideExternalUuids() {
        return Stream.of(
                Arguments.of((String) null),
                Arguments.of(UUID.randomUUID().toString())
        );
    }

    @BeforeEach
    void setUp() {
        diagram = Diagram.builder()
                .name(VALID_STR)
                .description(VALID_STR)
                .build();
    }

    @Test
    @DisplayName("find by existent uuid")
    void findDiagramByUuid_existentUuid_returnObj() {
        Diagram expected = diagramService.saveDiagram(diagram);
        Diagram actual = diagramService.findDiagramByUuid(expected.getUuid());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("save valid diagram")
    void saveDiagram_validObj_returnObj() {
        assertNull(diagram.getUuid());
        assertEquals(0, diagramRepository.count());
        diagramService.saveDiagram(diagram);
        assertNotNull(diagram.getUuid());
        assertEquals(1, diagramRepository.count());
    }

    @Test
    @DisplayName("update valid diagram")
    void updateDiagram_validObj_returnObj() {
        diagramService.saveDiagram(diagram);
        assertEquals(VALID_STR, diagram.getName());

        String newName = RandomStringUtils.random(127, true, true);
        diagram.setName(newName);

        diagramService.updateDiagram(diagram);
        assertEquals(diagram.getName(), newName);

    }

    @Test
    @DisplayName("delete existent diagram")
    void deleteDiagramByUuid_validUuid_returnObj() {
        diagramService.saveDiagram(diagram);
        assertEquals(1, diagramRepository.count());
        diagramService.deleteDiagramByUuid(diagram.getUuid());
        assertEquals(0, diagramRepository.count());
    }

    @Test
    @DisplayName("delete existent diagram")
    void findAllDiagrams__returnObj() {
        diagramService.saveDiagram(diagram);
        List<Diagram> diagrams = diagramService.findAllDiagrams();
        assertEquals(1, diagramRepository.count());
        assertThat(diagrams, hasItem(diagram));
    }

    @Test
    @DisplayName("new diagram")
    void newDiagram__returnObj() {
        assertEquals(0, diagramRepository.count());
        Diagram diagram = diagramService.newDiagram();
        assertNotNull(diagram.getUuid());
        assertEquals(1, diagramRepository.count());
    }

    @Test
    @DisplayName("new state for existent diagram")
    void newState__returnObj() {
        diagramService.saveDiagram(diagram);
        assertEquals(0, stateRepository.count());
        State state = diagramService.newState(diagram.getUuid());
        assertNotNull(state.getUuid());
        assertEquals(1, stateRepository.count());
    }

    @Test
    @DisplayName("delete state by existent state uuid for existent diagram")
    void deleteState__existentObj_returnObj() {
        diagramService.saveDiagram(diagram);
        State state = diagramService.newState(diagram.getUuid());

        assertEquals(1, diagram.getStates().size());

        diagramService.deleteState(diagram.getUuid(), state.getUuid());

        assertEquals(0, diagram.getStates().size());

    }

    @Test
    @DisplayName("delete state by existent state uuid for not allowing existent diagram")
    void deleteState_existentStateAndNotAllowingDiagram_returnObj() {
        diagramService.saveDiagram(diagram);
        diagramService.newState(diagram.getUuid());
        State state = stateService.newState();

        assertEquals(1, diagram.getStates().size());

        diagramService.deleteState(diagram.getUuid(), state.getUuid());

        assertEquals(1, diagram.getStates().size());

    }

    @Test
    @DisplayName("delete by non existent state uuid for existent diagram")
    void deleteState_nonExistentState_returnObj() {
        diagramService.saveDiagram(diagram);
        diagramService.newState(diagram.getUuid());

        assertThrows(NotFoundException.class, () -> diagramService.deleteState(diagram.getUuid(), RANDOM_UUID));

    }

    @ParameterizedTest
    @DisplayName("save external diagram with uuids")
    @MethodSource("provideExternalUuids")
    void saveExternalDiagram__returnObj(String externalDiagramUuid) {
        diagramService.saveDiagram(diagram);

        assertEquals(1, diagramRepository.count());

        Diagram externalDiagram = Diagram.builder()
                .uuid(externalDiagramUuid)
                .name(VALID_STR)
                .description(VALID_STR).build();

        diagramService.saveExternalDiagram(externalDiagram);

        assertNotNull(externalDiagram.getUuid());
        assertEquals(2, diagramRepository.count());
    }

    @TestFactory
    Stream<DynamicTest> dynamicTests() {
        return Stream.of(
                dynamicTest(
                        "find diagram by null uuid",
                        () -> assertThrows(DataAccessException.class, () -> diagramService.findDiagramByUuid(null))
                ),

                dynamicTest(
                        "find diagram by non existent uuid",
                        () -> assertNull(diagramService.findDiagramByUuid(RANDOM_UUID))
                ),
                dynamicTest(
                        "save null diagram",
                        () -> assertThrows(NullPointerException.class, () -> diagramService.saveDiagram(null))
                ),
                dynamicTest(
                        "update null diagram",
                        () -> assertThrows(NullPointerException.class, () -> diagramService.updateDiagram(null))
                ),
                dynamicTest(
                        "delete by null diagram uuid",
                        () -> assertThrows(DataAccessException.class, () -> diagramService.deleteDiagramByUuid(null))
                ),
                dynamicTest(
                        "delete by non existent diagram uuid",
                        () -> assertThrows(DataAccessException.class, () -> diagramService.deleteDiagramByUuid(RANDOM_UUID))
                ),
                dynamicTest(
                        "delete by non null diagram uuid",
                        () -> assertThrows(DataAccessException.class, () -> diagramService.newState(null))
                ),
                dynamicTest(
                        "new state for non existent diagram",
                        () -> assertThrows(NotFoundException.class, () -> diagramService.newState(RANDOM_UUID))
                ),
                dynamicTest(
                        "delete state by existing uuid for null diagram",
                        () -> assertThrows(DataAccessException.class, () -> {
                            State state = stateService.newState();
                            diagramService.deleteState(null, state.getUuid());
                        })
                ),
                dynamicTest(
                        "delete state by non exisitng uuid for null diagram",
                        () -> assertThrows(DataAccessException.class, () -> diagramService.deleteState(null, RANDOM_UUID))
                ),
                dynamicTest(
                        "delete state by null uuid for null diagram",
                        () -> assertThrows(DataAccessException.class, () -> diagramService.deleteState(null, null))
                ),
                dynamicTest(
                        "save null external diagram",
                        () -> assertThrows(NullPointerException.class, () -> diagramService.saveExternalDiagram(null))
                )
        );
    }


}