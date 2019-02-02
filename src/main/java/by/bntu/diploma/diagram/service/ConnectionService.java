package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.entity.Connection;

import java.util.List;

public interface ConnectionService {

    Connection saveConnection(Connection connection);

    List<Connection> saveAllConnections(List<Connection> connections);

}
