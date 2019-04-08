package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.domain.Target;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface TargetService {

    Target saveTarget(@Valid Target target);

    List<Target> saveAllTargets(List<@Valid Target> targets);

    Target newTarget();

    Target findByTargetUUID(
            @NotNull(message = "{target.uuid.null}") @Min(value = 1, message = "{target.uuid.min}") Long targetUUID
    );
}
