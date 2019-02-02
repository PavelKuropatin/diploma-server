package by.bntu.diploma.diagram.service;

import by.bntu.diploma.diagram.entity.Target;

import java.util.List;

public interface TargetService {

    Target saveTarget(Target target);

    List<Target> saveAllTargets(List<Target> targets);

    Target newTarget();

    Target findByTargetUUID(Long targetUUID);
}
