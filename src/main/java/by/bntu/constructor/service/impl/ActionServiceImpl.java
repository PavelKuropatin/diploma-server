package by.bntu.constructor.service.impl;

import by.bntu.constructor.domain.Action;
import by.bntu.constructor.repository.ActionRepository;
import by.bntu.constructor.service.ActionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Service
@Validated
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ActionServiceImpl implements ActionService {

    private final ActionRepository actionRepository;

    @Override
    public Action findActionByUuid(@Valid String actionUuid) {
        Optional<Action> actionOptional = actionRepository.findById(actionUuid);
        return actionOptional.orElse(null);
    }

    @Override
    @Transactional
    public List<Action> saveAllActions(@Valid List<Action> actions) {
        return actionRepository.saveAll(actions);
    }

    @Override
    @Transactional
    public Action saveAction(@Valid Action action) {
        return actionRepository.save(action);
    }
}
