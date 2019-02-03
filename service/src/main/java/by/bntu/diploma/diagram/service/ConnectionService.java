package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.domain.Connection;

import javax.validation.Valid;
import java.util.List;

public interface ConnectionService {

    Connection saveConnection(@Valid Connection connection);

    List<Connection> saveAllConnections(List<@Valid Connection> connections);

}
