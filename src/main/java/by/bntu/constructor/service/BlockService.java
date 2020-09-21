package by.bntu.constructor.service;

import by.bntu.constructor.domain.*;
import by.bntu.constructor.domain.constraint.util.ValidationMessage;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface BlockService {

    Block saveBlock(@Valid Block block);

    List<Block> saveAllBlocks(List<@Valid Block> blocks);

    List<Block> saveExternalBlocks(List<@Valid Block> blocks);


    Block findByBlockUuid(
            @NotNull(message = ValidationMessage.Block.UUID_NULL) String blockUuid
    );

    Block newBlock();

    Input newInput(
            @NotNull(message = ValidationMessage.Block.UUID_NULL) String blockUuid
    );

    Output newOutput(
            @NotNull(message = ValidationMessage.Block.UUID_NULL) String blockUuid
    );

    void deleteInput(
            @NotNull(message = ValidationMessage.Block.UUID_NULL) String blockUuid,
            @NotNull(message = ValidationMessage.Input.UUID_NULL) String inputUuid
    );

    void deleteOutput(
            @NotNull(message = ValidationMessage.Block.UUID_NULL) String blockUuid,
            @NotNull(message = ValidationMessage.Output.UUID_NULL) String outputUuid
    );

    Block putVar(
            @NotNull(message = ValidationMessage.Block.UUID_NULL) String blockUuid,
            @Valid Variable variable
    );


    Block deleteVar(
            @NotNull(message = ValidationMessage.Block.UUID_NULL) String blockUuid,
            @Valid Variable variable
    );

    Block addConnection(
            @NotNull(message = ValidationMessage.Block.UUID_NULL) String blockUuid,
            @Valid Connection connection
    );

    Block deleteConnection(
            @NotNull(message = ValidationMessage.Block.UUID_NULL) String blockUuid,
            @Valid Connection connection
    );
}
