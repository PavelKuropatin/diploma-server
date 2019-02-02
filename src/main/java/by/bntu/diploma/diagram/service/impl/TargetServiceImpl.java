package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.entity.Target;
import by.bntu.diploma.diagram.repository.TargetRepository;
import by.bntu.diploma.diagram.service.TargetService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TargetServiceImpl implements TargetService {

    private TargetRepository targetRepo;

    @Override
    @Transactional
    public Target saveTarget(@Valid Target target) {
        return this.targetRepo.save(target);
    }

    @Override
    @Transactional
    public List<Target> saveAllTargets(List<Target> targets) {
        return targets.stream().map(this::saveTarget).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Target newTarget() {
        Target target = Target.builder().build();
        return this.saveTarget(target);
    }

    @Override
    public Target findByTargetUUID(Long targetUUID) {
        if (targetUUID == null || targetUUID < 1) {
            throw new IllegalArgumentException("Target UUID is null or less then 1. Got " + targetUUID);
        }
        Optional<Target> targetEndpointOptional = this.targetRepo.findById(targetUUID);
        return targetEndpointOptional.orElse(null);
    }
}