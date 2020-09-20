package by.bntu.constructor.web.dto.mapper.impl;

import by.bntu.constructor.domain.Connection;
import by.bntu.constructor.domain.Output;
import by.bntu.constructor.web.dto.ConnectionDTO;
import by.bntu.constructor.web.dto.mapper.Mapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class ConnectionMapper implements Mapper<Connection, ConnectionDTO> {

    @Mapping(target = "output", ignore = true)
    @Mapping(target = "input", ignore = true)
    @Override
    public abstract Connection fromDTO(ConnectionDTO connectionDTO);

    @Mapping(target = "outputUuid", ignore = true)
    @Override
    public abstract ConnectionDTO toDTO(Connection connection);

    @AfterMapping
    void setValuesToObj(ConnectionDTO connectionDTO, @MappingTarget Connection connection) {
        if (connection.getVisible() == null) {
            connection.setVisible(true);
        }
        connection.setOutput(Output.builder().uuid(connectionDTO.getOutputUuid()).build());
    }

    @AfterMapping
    void setValuesToDTO(Connection connection, @MappingTarget ConnectionDTO connectionDTO) {
        connectionDTO.setOutputUuid(connection.getOutput().getUuid());
    }


}
