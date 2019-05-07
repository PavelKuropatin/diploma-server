package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.domain.Target;
import by.bntu.diploma.diagram.domain.constraint.util.ValidationMessage;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface TargetService {

    Target saveTarget(@Valid Target target);

    List<Target> saveAllTargets(List<@Valid Target> targets);

    Target newTarget();

    Target findByTargetUuid(
            @NotNull(message = ValidationMessage.Target.UUID_NULL)
                    String targetUuid
    );
}
