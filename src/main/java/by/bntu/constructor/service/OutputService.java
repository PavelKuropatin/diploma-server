package by.bntu.constructor.service;

import by.bntu.constructor.domain.Output;
import by.bntu.constructor.domain.constraint.util.ValidationMessage;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface OutputService {

    Output saveOutput(@Valid Output output);

    List<Output> saveAllOutputs(List<@Valid Output> outputs);

    Output newOutput();

    Output findByOutputUuid(
            @NotNull(message = ValidationMessage.Output.UUID_NULL)
                    String outputUuid
    );
}
