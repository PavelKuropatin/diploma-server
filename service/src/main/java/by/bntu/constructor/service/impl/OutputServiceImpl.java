package by.bntu.constructor.service.impl;

import by.bntu.constructor.domain.Output;
import by.bntu.constructor.repository.OutputRepository;
import by.bntu.constructor.service.OutputService;
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
public class OutputServiceImpl implements OutputService {

    private final OutputRepository outputRepository;

    @Override
    @Transactional
    public Output saveOutput(Output output) {
        return outputRepository.save(output);
    }

    @Override
    @Transactional
    public List<Output> saveAllOutputs(List<Output> outputs) {
        outputs.stream()
                .filter(output -> output.getUuid() != null)
                .filter(output -> !outputRepository.existsById(output.getUuid()))
                .forEach(output -> output.setUuid(null));
        return outputRepository.saveAll(outputs);
    }

    @Override
    @Transactional
    public Output newOutput() {
        Output output = Output.builder().build();
        return saveOutput(output);
    }

    @Override
    public Output findByOutputUuid(String outputUuid) {
        Optional<Output> outputEndpointOptional = outputRepository.findById(outputUuid);
        return outputEndpointOptional.orElse(null);
    }

}
