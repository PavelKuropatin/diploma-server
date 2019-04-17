package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.domain.Connection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface ConnectionService {

    Connection saveConnection(@NotNull @Valid Connection connection);

    List<Connection> saveAllConnections(@NotNull List<@Valid Connection> connections);

}
