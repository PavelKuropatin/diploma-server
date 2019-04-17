package by.bntu.diploma.diagram.service.utils;

import by.bntu.diploma.diagram.domain.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class DomainUtils {
    private DomainUtils() {

    }

    public static List<Target> extractTargetsFromState(@NotNull @Valid State state) {
        List<Target> targets = new LinkedList<>();
        targets.addAll(state.getTargets());
        targets.addAll(extractTargetsFromSources(state.getSources()));
        return targets.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public static List<Target> extractTargetsFromStates(@NotNull List<@Valid State> states) {
        List<Target> targets = new LinkedList<>();
        states.forEach(state -> targets.addAll(extractTargetsFromState(state)));
        return targets.stream().distinct().collect(Collectors.toList());
    }

    public static List<Target> extractTargetsFromConnections(@NotNull List<@Valid Connection> connections) {
        return connections.stream()
                .map(Connection::getTarget)
                .distinct()
                .collect(Collectors.toList());
    }

    public static List<Target> extractTargetsFromSource(@NotNull @Valid Source source) {
        return extractTargetsFromConnections(source.getConnections());
    }

    public static List<Target> extractTargetsFromSources(@NotNull List<@Valid Source> sources) {
        return sources.stream()
                .map(DomainUtils::extractTargetsFromSource)
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    public static List<Source> extractSourcesFromStates(@NotNull List<@Valid State> states) {
        return states.stream()
                .map(State::getSources)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public static List<Style> extractStyleFromStates(@NotNull List<@Valid State> states) {
        return states.stream().map(State::getStyle).collect(Collectors.toList());
    }


    public static void dropDuplicateTargets(@NotNull List<@Valid State> states, @NotNull List<@Valid Target> targetPool) {
        Map<Long, Target> targets = targetPool.stream().collect(Collectors.toMap(Target::getUuid, x -> x));
        states.forEach(state -> {
            state.getSources().forEach(source -> {
                source.getConnections().forEach(connection -> {
                    Long targetUUID = connection.getTarget().getUuid();
                    connection.setTarget(targets.get(targetUUID));
                });
            });
            state.setTargets(targets.values().stream().filter(target -> state.getTargets().contains(target)).collect(Collectors.toList()));
        });

    }

    public static List<Connection> extractConnectionsFromSources(List<Source> sources) {
        return sources.stream()
                .map(Source::getConnections)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
