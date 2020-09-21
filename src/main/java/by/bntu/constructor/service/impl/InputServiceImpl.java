package by.bntu.constructor.service.impl;

import by.bntu.constructor.domain.Input;
import by.bntu.constructor.repository.InputRepository;
import by.bntu.constructor.service.InputService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class InputServiceImpl implements InputService {

    private final InputRepository inputRepository;

    @Override
    @Transactional
    public Input saveInput(Input input) {
        return inputRepository.save(input);
    }

    @Override
    @Transactional
    public List<Input> saveAllInputs(List<Input> inputs) {
        inputs.stream()
                .filter(input -> input.getUuid() != null)
                .filter(input -> !inputRepository.existsById(input.getUuid()))
                .forEach(input -> input.setUuid(null));
        return inputRepository.saveAll(inputs);
    }

    @Override
    @Transactional
    public Input newInput() {
        Input input = Input.builder().build();
        return saveInput(input);
    }

    @Override
    public Input findByInputUuid(String inputUuid) {
        Optional<Input> inputEndpointOptional = inputRepository.findById(inputUuid);
        return inputEndpointOptional.orElse(null);
    }
}
