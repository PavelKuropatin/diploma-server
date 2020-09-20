package by.bntu.constructor.service.utils;

import by.bntu.constructor.domain.Block;
import by.bntu.constructor.domain.Input;
import by.bntu.constructor.domain.Output;
import by.bntu.constructor.domain.Style;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class DomainUtils {
    private DomainUtils() {

    }

    public static List<Output> extractOutputsFromBlocks(@NotNull List<@Valid Block> blocks) {
        return blocks.stream()
                .map(Block::getOutputs)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public static List<Input> extractInputsFromBlocks(@NotNull List<@Valid Block> blocks) {
        return blocks.stream()
                .map(Block::getInputs)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public static List<Style> extractStyleFromBlock(@NotNull List<@Valid Block> blocks) {
        return blocks.stream()
                .map(Block::getStyle)
                .collect(Collectors.toList());
    }

    public static void dropDuplicateRefs(@NotNull List<@Valid Block> blocks) {
        Map<String, Output> outputs = extractOutputsFromBlocks(blocks).stream()
                .collect(Collectors.toMap(Output::getUuid, t -> t, (t1, t2) -> t1));
        Map<String, Input> inputs = extractInputsFromBlocks(blocks).stream()
                .collect(Collectors.toMap(Input::getUuid, s -> s, (s1, s2) -> s1));
        blocks.forEach(block -> block.getConnections()
                .forEach(connection -> {
                    connection.setInput(inputs.get(connection.getInput().getUuid()));
                    connection.setOutput(outputs.get(connection.getOutput().getUuid()));
                })
        );
    }


    public static void dropOutputs(@NotNull List<@Valid Block> blocks, @NotNull List<@Valid Output> outputs) {
        outputs.forEach(t ->
                blocks.forEach(st ->
                        st.getConnections().removeIf(c -> c.getOutput().getUuid().equals(t.getUuid()))
                )
        );
    }

    public static void dropInputs(@NotNull List<@Valid Block> blocks, @NotNull List<@Valid Input> inputs) {
        inputs.forEach(s ->
                blocks.forEach(st ->
                        st.getConnections().removeIf(c -> c.getInput().getUuid().equals(s.getUuid()))
                )
        );

    }


}
