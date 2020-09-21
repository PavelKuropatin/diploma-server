package by.bntu.constructor.service;

import by.bntu.constructor.domain.Input;
import by.bntu.constructor.domain.constraint.util.ValidationMessage;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface InputService {

    Input saveInput(@Valid Input input);

    List<Input> saveAllInputs(List<@Valid Input> inputs);

    Input newInput();

    Input findByInputUuid(
            @NotNull(message = ValidationMessage.Input.UUID_NULL) String inputUuid
    );
}
