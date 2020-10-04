package by.bntu.constructor.service;

import by.bntu.constructor.domain.Action;
import by.bntu.constructor.domain.Settings;

import javax.validation.Valid;
import java.util.List;

public interface ActionService {


    Action findActionByUuid(@Valid String actionUuid);

    List<Action> saveAllActions(@Valid List<Action> actions);

    Action saveAction(@Valid Action action);

}
