package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.domain.Connection;
import by.bntu.diploma.diagram.domain.Target;
import by.bntu.diploma.diagram.repository.ConnectionRepository;
import by.bntu.diploma.diagram.service.TargetService;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UnitConnectionServiceImplTest {

    private static final int TEST_SIZE = 3;
    private static final String RANDOM_UUID = UUID.randomUUID().toString();

    private final Answer<Connection> setConnectionUuid = invocation -> {
        Connection connection = invocation.getArgument(0);
        if (connection == null) {
            throw new NullPointerException();
        }
        connection.setUuid(UUID.randomUUID().toString());
        return connection;
    };

    private final Answer<List<Connection>> setConnectionsUuid = invocation -> {
        List<Connection> connections = invocation.getArgument(0);
        if (connections.contains(null)) {
            throw new NullPointerException();
        }
        connections.forEach(connection -> connection.setUuid(UUID.randomUUID().toString()));
        return connections;
    };

    @InjectMocks
    private ConnectionServiceImpl connectionService;

    @Mock
    private ConnectionRepository connectionRepository;

    @Mock
    private TargetService targetService;

    @Test
    @DisplayName("save valid connection")
    void saveConnection_validObj_returnObj() {
        when(connectionRepository.save(any(Connection.class))).thenAnswer(setConnectionUuid);

        Target target = Target.builder().build();
        Connection connection = Connection.builder().target(target).build();

        assertNull(connection.getUuid());
        connection = connectionService.saveConnection(connection);

        assertNotNull(connection.getUuid());
    }

    @Test
    @DisplayName("save invalid connection")
    void saveConnection_invalidObj_returnObj() {
        when(connectionRepository.save(any(Connection.class))).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> connectionService.saveConnection(Connection.builder().build()));
    }

    @Test
    @DisplayName("save valid connections")
    void saveAllConnections_validConnections_returnObj() {
        when(connectionRepository.saveAll(anyList())).thenAnswer(setConnectionsUuid);

        List<Connection> connections = LongStream.range(0, TEST_SIZE)
                .boxed()
                .map(target -> Connection.builder().target(new Target()).build())
                .collect(Collectors.toList());

        connectionService.saveAllConnections(connections);

        List<String> actualUuids = connections.stream().map(Connection::getUuid).sorted().collect(Collectors.toList());
        for (String uuid : actualUuids) {
            assertNotNull(uuid);
        }
    }


    @Test
    @DisplayName("save invalid connections")
    void saveAllConnections_containsInvalidConnection_returnException() {
        when(targetService.saveAllTargets(anyList())).thenThrow(NullPointerException.class);

        List<Connection> connections = LongStream.range(0, TEST_SIZE)
                .boxed()
                .map(target -> Connection.builder().target(new Target()).build())
                .collect(Collectors.toList());

        connections.get(RandomUtils.nextInt(0, connections.size())).setTarget(null);

        assertThrows(NullPointerException.class, () -> connectionService.saveAllConnections(connections));
    }

}