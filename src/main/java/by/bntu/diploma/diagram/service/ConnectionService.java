package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.entity.Connection;

import java.util.List;

public interface ConnectionService {

    Connection save(Connection connection);

    List<Connection> saveAll(List<Connection> connections);

}
