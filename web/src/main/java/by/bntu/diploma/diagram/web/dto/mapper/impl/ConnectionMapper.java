package by.bntu.diploma.diagram.web.dto.mapper.impl;

import by.bntu.diploma.diagram.domain.Connection;
import by.bntu.diploma.diagram.domain.Target;
import by.bntu.diploma.diagram.web.dto.ConnectionDTO;
import by.bntu.diploma.diagram.web.dto.mapper.Mapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class ConnectionMapper implements Mapper<Connection, ConnectionDTO> {

    @Mapping(target = "target", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Override
    public abstract Connection fromDTO(ConnectionDTO connectionDTO);

    @Mapping(target = "targetPointUUID", ignore = true)
    @Override
    public abstract ConnectionDTO toDTO(Connection connection);

    @AfterMapping
    void setValuesToObj(ConnectionDTO connectionDTO, @MappingTarget Connection connection) {
        connection.setTarget(Target.builder().uuid(connectionDTO.getTargetPointUUID()).build());
    }

    @AfterMapping
    void setValuesToDTO(Connection connection, @MappingTarget ConnectionDTO connectionDTO) {
        connectionDTO.setTargetPointUUID(connection.getTarget().getUuid());
    }


}
