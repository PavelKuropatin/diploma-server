package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.entity.Connection;
import by.bntu.diploma.diagram.repository.ConnectionRepository;
import by.bntu.diploma.diagram.service.ConnectionService;
import by.bntu.diploma.diagram.service.TargetEndpointService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ConnectionServiceImpl implements ConnectionService {

    private ConnectionRepository connectionRepo;
    private TargetEndpointService targetEndpointService;

    @Override
    @Transactional
    public Connection save(@Valid Connection connection) {
        connection.setTargetEndpoint(this.targetEndpointService.save(connection.getTargetEndpoint()));
        return this.connectionRepo.save(connection);
    }

    @Override
    @Transactional
    public List<Connection> saveAll(List<Connection> connections) {
        return connections.stream().map(this::save).collect(Collectors.toList());
    }

}
