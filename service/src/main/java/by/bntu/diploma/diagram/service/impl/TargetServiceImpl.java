package by.bntu.diploma.diagram.service.impl;

import by.bntu.diploma.diagram.domain.Target;
import by.bntu.diploma.diagram.repository.TargetRepository;
import by.bntu.diploma.diagram.service.TargetService;
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
public class TargetServiceImpl implements TargetService {

    private TargetRepository targetRepository;

    @Override
    @Transactional
    public Target saveTarget(Target target) {
        return targetRepository.save(target);
    }

    @Override
    @Transactional
    public List<Target> saveAllTargets(List<Target> targets) {
        targets.stream()
                .filter(target -> target.getUuid() != null)
                .filter(target -> !targetRepository.existsById(target.getUuid()))
                .forEach(target -> target.setUuid(null));
        return targetRepository.saveAll(targets);
    }

    @Override
    @Transactional
    public Target newTarget() {
        Target target = Target.builder().build();
        return saveTarget(target);
    }

    @Override
    public Target findByTargetUuid(String targetUuid) {
        Optional<Target> targetEndpointOptional = targetRepository.findById(targetUuid);
        return targetEndpointOptional.orElse(null);
    }

}
