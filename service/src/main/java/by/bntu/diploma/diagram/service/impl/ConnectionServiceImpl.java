package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.domain.Connection;
import by.bntu.diploma.diagram.domain.Target;
import by.bntu.diploma.diagram.repository.ConnectionRepository;
import by.bntu.diploma.diagram.service.ConnectionService;
import by.bntu.diploma.diagram.service.TargetService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ConnectionServiceImpl implements ConnectionService {

    private ConnectionRepository connectionRepository;
    private TargetService targetService;

    @Override
    @Transactional
    public Connection saveConnection(Connection connection) {
        connection.setTarget(targetService.saveTarget(connection.getTarget()));
        return connectionRepository.save(connection);
    }

    @Override
    @Transactional
    public List<Connection> saveAllConnections(List<Connection> connections) {
        List<Target> targetsToSave = connections.stream()
                .map(Connection::getTarget)
                .collect(Collectors.toList());
        targetService.saveAllTargets(targetsToSave);
        return connectionRepository.saveAll(connections);
    }

}
