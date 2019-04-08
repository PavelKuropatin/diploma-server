package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.domain.Connection;
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

    private ConnectionRepository connectionRepo;
    private TargetService targetService;

    @Override
    @Transactional
    public Connection saveConnection(Connection connection) {
        connection.setTarget(this.targetService.saveTarget(connection.getTarget()));
        return this.connectionRepo.save(connection);
    }

    @Override
    @Transactional
    public List<Connection> saveAllConnections(List<Connection> connections) {
        return connections.stream().map(this::saveConnection).collect(Collectors.toList());
    }

}
