package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.domain.Target;

import javax.validation.Valid;
import java.util.List;

public interface TargetService {

    Target saveTarget(@Valid Target target);

    List<Target> saveAllTargets(List<@Valid Target> targets);

    Target newTarget();

    Target findByTargetUUID(Long targetUUID);
}
