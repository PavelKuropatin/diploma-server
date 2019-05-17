package by.bntu.diploma.diagram.service.utils;

import by.bntu.diploma.diagram.domain.Source;
import by.bntu.diploma.diagram.domain.State;
import by.bntu.diploma.diagram.domain.Style;
import by.bntu.diploma.diagram.domain.Target;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class DomainUtils {
    private DomainUtils() {

    }

    public static List<Target> extractTargetsFromStates(@NotNull List<@Valid State> states) {
        return states.stream()
                .map(State::getTargets)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public static List<Source> extractSourcesFromStates(@NotNull List<@Valid State> states) {
        return states.stream()
                .map(State::getSources)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public static List<Style> extractStyleFromStates(@NotNull List<@Valid State> states) {
        return states.stream()
                .map(State::getStyle)
                .collect(Collectors.toList());
    }

    public static void dropDuplicateRefs(@NotNull List<@Valid State> states) {
        Map<String, Target> targets = extractTargetsFromStates(states).stream()
                .collect(Collectors.toMap(Target::getUuid, t -> t, (t1, t2) -> t1));
        Map<String, Source> sources = extractSourcesFromStates(states).stream()
                .collect(Collectors.toMap(Source::getUuid, s -> s, (s1, s2) -> s1));
        states.forEach(state -> state.getConnections()
                .forEach(connection -> {
                    connection.setSource(sources.get(connection.getSource().getUuid()));
                    connection.setTarget(targets.get(connection.getTarget().getUuid()));
                })
        );
    }

}
