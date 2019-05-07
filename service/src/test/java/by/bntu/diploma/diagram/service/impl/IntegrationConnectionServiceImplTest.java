package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.domain.Connection;
import by.bntu.diploma.diagram.domain.Target;
import by.bntu.diploma.diagram.repository.ConnectionRepository;
import by.bntu.diploma.diagram.service.ConnectionService;
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
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;


@SpringJUnitConfig(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class IntegrationConnectionServiceImplTest {

    private static final int TEST_SIZE = 3;

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private ConnectionRepository connectionRepository;


    @Test
    @DisplayName("save valid connection")
    void saveConnection_validObj_returnObj() {
        Target target = Target.builder().build();
        Connection connection = Connection.builder().target(target).build();

        assertNull(connection.getUuid());

        connection = connectionService.saveConnection(connection);

        assertNotNull(connection.getUuid());
    }

    @Test
    @DisplayName("save valid connections")
    void saveAllConnections_validConnections_returnObj() {
        List<Connection> connections = LongStream.range(0, TEST_SIZE)
                .boxed()
                .map(target -> Connection.builder().target(new Target()).build())
                .collect(Collectors.toList());

        connectionService.saveAllConnections(connections);

        List<String> actualUuids = connections.stream().map(Connection::getUuid).sorted().collect(Collectors.toList());
        for (String uuid : actualUuids) {
            assertNotNull(uuid);
        }
        assertEquals(TEST_SIZE, connectionRepository.count());
    }

    @Test
    @DisplayName("save invalid connections")
    void saveAllConnections_containsInvalidConnection_returnException() {
        List<Connection> connections = LongStream.range(0, TEST_SIZE)
                .boxed()
                .map(target -> Connection.builder().target(new Target()).build())
                .collect(Collectors.toList());

        connections.get(RandomUtils.nextInt(0, connections.size())).setTarget(null);

        assertThrows(DataAccessException.class, () -> connectionService.saveAllConnections(connections));
        assertEquals(0, connectionRepository.count());
    }

    @TestFactory
    Stream<DynamicTest> dynamicTests() {
        return Stream.of(
                dynamicTest(
                        "save null connections",
                        () -> assertThrows(NullPointerException.class, () -> connectionService.saveAllConnections(null))
                ),
                dynamicTest(
                        "save invalid connection",
                        () -> assertThrows(DataAccessException.class, () -> connectionService.saveConnection(new Connection()))
                ),
                dynamicTest(
                        "save null connection",
                        () -> assertThrows(NullPointerException.class, () -> connectionService.saveConnection(null))
                )
        );
    }
}