package by.bntu.constructor.service.impl;

import by.bntu.constructor.domain.*;
import by.bntu.constructor.repository.BlockRepository;
import by.bntu.constructor.service.BlockService;
import by.bntu.constructor.service.InputService;
import by.bntu.constructor.service.OutputService;
import by.bntu.constructor.service.StyleService;
import by.bntu.constructor.service.exception.NotFoundException;
import by.bntu.constructor.service.utils.DomainUtils;
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
public class BlockServiceImpl implements BlockService {

    private final BlockRepository blockRepository;
    private final OutputService outputService;
    private final InputService inputService;
    private final StyleService styleService;


    @Override
    public Block findByBlockUuid(String blockUuid) {
        Optional<Block> blockOptional = blockRepository.findById(blockUuid);
        return blockOptional.orElse(null);
    }

    @Override
    @Transactional
    public List<Block> saveAllBlocks(List<Block> blocks) {
        DomainUtils.dropDuplicateRefs(blocks);

        List<Style> styles = DomainUtils.extractStyleFromBlock(blocks);
        List<Output> outputs = DomainUtils.extractOutputsFromBlocks(blocks);
        List<Input> inputs = DomainUtils.extractInputsFromBlocks(blocks);

        styleService.saveAllStyles(styles);
        outputService.saveAllOutputs(outputs);
        inputService.saveAllInputs(inputs);

        blocks.stream()
                .filter(block -> block.getUuid() != null)
                .filter(block -> !blockRepository.existsById(block.getUuid()))
                .forEach(block -> block.setUuid(null));
        return blockRepository.saveAll(blocks);
    }

    @Override
    @Transactional
    public List<Block> saveExternalBlocks(List<Block> blocks) {
        blocks.forEach(block -> block.setUuid(null));

        DomainUtils.dropDuplicateRefs(blocks);

        List<Style> styles = DomainUtils.extractStyleFromBlock(blocks);
        List<Output> outputs = DomainUtils.extractOutputsFromBlocks(blocks);
        List<Input> inputs = DomainUtils.extractInputsFromBlocks(blocks);

        styles.forEach(style -> style.setUuid(null));
        outputs.forEach(output -> output.setUuid(null));
        inputs.forEach(input -> input.setUuid(null));

        styleService.saveAllStyles(styles);
        outputService.saveAllOutputs(outputs);
        inputService.saveAllInputs(inputs);

        return blockRepository.saveAll(blocks);
    }

    @Override
    @Transactional
    public Block saveBlock(Block block) {
        styleService.saveStyle(block.getStyle());
        outputService.saveAllOutputs(block.getOutputs());
        inputService.saveAllInputs(block.getInputs());
        return blockRepository.save(block);
    }

    @Override
    @Transactional
    public Block newBlock() {
        Block block;
        block = Block.builder()
                .color("#CC1A55")
                .name("New Block")
                .style(Style.builder()
                        .inputAnchorStyle("RightMiddle")
                        .outputAnchorStyle("LeftMiddle")
                        .inputStyle("endpoint-style-right")
                        .outputStyle("endpoint-style-left")
                        .build())
                .positionX(10.0)
                .positionY(10.0)
                .template("action")
                .build();
        block = saveBlock(block);
        newInput(block.getUuid());
        newOutput(block.getUuid());
        return block;
    }

    @Override
    @Transactional
    public Input newInput(String blockUuid) {
        Block block = findByBlockUuid(blockUuid);
        if (block == null) {
            throw new NotFoundException(Block.class, blockUuid);
        }
        Input input = inputService.newInput();
        block.getInputs().add(input);
        saveBlock(block);
        return input;
    }

    @Override
    @Transactional
    public Output newOutput(String blockUuid) {
        Block block = findByBlockUuid(blockUuid);
        if (block == null) {
            throw new NotFoundException(Block.class, blockUuid);
        }
        Output output = outputService.newOutput();
        block.getOutputs().add(output);
        saveBlock(block);
        return output;
    }

    @Override
    @Transactional
    public void deleteInput(String blockUuid, String inputUuid) {
        Block block = findByBlockUuid(blockUuid);
        Input input = inputService.findByInputUuid(inputUuid);
        if (block == null) {
            throw new NotFoundException(Block.class, blockUuid);
        }
        if (input == null) {
            throw new NotFoundException(Input.class, inputUuid);
        }
        if (!block.getInputs().contains(input)) {
            throw new NotFoundException("Block[" + blockUuid + "] not contain Input[" + inputUuid + "]. Deleting useless.");
        }

        List<Block> blocks = blockRepository.findByInputUuid(inputUuid);
        DomainUtils.dropInputs(blocks, block.getInputs());

        blocks.forEach(b -> {
            b.getConnections().removeIf(c -> c.getInput().getUuid().equals(inputUuid));
            b.getInputs().removeIf(i -> i.getUuid().equals(inputUuid));
        });
        block.getInputs().removeIf(i -> i.getUuid().equals(inputUuid));

        saveAllBlocks(blocks);
    }

    @Override
    @Transactional
    public void deleteOutput(String blockUuid, String outputUuid) {
        Block block = findByBlockUuid(blockUuid);
        Output output = outputService.findByOutputUuid(outputUuid);
        if (block == null) {
            throw new NotFoundException(Block.class, blockUuid);
        }
        if (output == null) {
            throw new NotFoundException(Output.class, outputUuid);
        }
        if (!block.getOutputs().contains(output)) {
            throw new IllegalArgumentException("Block[" + blockUuid + "] not contain Output[" + outputUuid + "]. Deleting useless.");
        }

        List<Block> blocks = blockRepository.findByOutputUuid(outputUuid);
        DomainUtils.dropOutputs(blocks, block.getOutputs());
        block.getOutputs().removeIf(o -> o.getUuid().equals(output.getUuid()));

        saveAllBlocks(blocks);
    }

    @Override
    @Transactional
    public Block putVar(String blockUuid, Variable variable) {
        Block block = findByBlockUuid(blockUuid);
        if (block == null) {
            throw new NotFoundException(Block.class, blockUuid);
        }

        List<Variable> variables;
        Variable.Type type = variable.getType();
        if (type == Variable.Type.INPUT) {
            variable.setFunction(null);
            variables = block.getInputVars();
        } else {
            variables = block.getOutputVars();
        }

        Optional<Variable> optional = variables.stream()
                .filter(v -> v.getParam().equals(variable.getParam()))
                .findFirst();
        if (optional.isPresent()) {
            throw new IllegalArgumentException("Already exist");
        }

        variables.add(variable);
        block = saveBlock(block);
        return block;
    }

    @Override
    @Transactional
    public Block deleteVar(String blockUuid, Variable variable) {
        Block block = findByBlockUuid(blockUuid);
        if (block == null) {
            throw new NotFoundException(Block.class, blockUuid);
        }

        List<Variable> variables;
        if (variable.getType() == Variable.Type.INPUT) {
            variables = block.getInputVars();
        } else {
            variables = block.getOutputVars();
        }

        Optional<Variable> optional = variables.stream()
                .filter(v -> v.getParam().equals(variable.getParam()))
                .findFirst();
        if (!optional.isPresent()) {
            throw new IllegalArgumentException("Do not exist");
        }

        variables.removeIf(v -> v.getParam().equals(variable.getParam()));
        block = saveBlock(block);
        return block;
    }

    @Override
    @Transactional
    public Block addConnection(String blockUuid, Connection connection) {
        Block block = findByBlockUuid(blockUuid);
        if (block == null) {
            throw new NotFoundException(Block.class, blockUuid);
        }
        Input input = inputService.findByInputUuid(connection.getInput().getUuid());
        if (input == null) {
            throw new NotFoundException(Input.class, connection.getInput().getUuid());
        }
        Output output = outputService.findByOutputUuid(connection.getOutput().getUuid());
        if (output == null) {
            throw new NotFoundException(Output.class, connection.getOutput().getUuid());
        }

        connection.setInput(input);
        connection.setOutput(output);

        block.getConnections().add(connection);
        block = saveBlock(block);
        return block;
    }

    @Override
    @Transactional
    public Block deleteConnection(String blockUuid, Connection connection) {
        Block block = findByBlockUuid(blockUuid);
        if (block == null) {
            throw new NotFoundException(Block.class, blockUuid);
        }
        Input input = inputService.findByInputUuid(connection.getInput().getUuid());
        if (input == null) {
            throw new NotFoundException(Input.class, connection.getInput().getUuid());
        }
        Output output = outputService.findByOutputUuid(connection.getOutput().getUuid());
        if (output == null) {
            throw new NotFoundException(Output.class, connection.getOutput().getUuid());
        }
        if (!block.getConnections().contains(connection)) {
            throw new IllegalArgumentException("Do not exist");
        }
        block.getConnections().remove(connection);
        block = saveBlock(block);
        return block;
    }
}
