package by.bntu.constructor.service.impl;

import by.bntu.constructor.domain.Action;
import by.bntu.constructor.domain.Settings;
import by.bntu.constructor.repository.SettingsRepository;
import by.bntu.constructor.service.ActionService;
import by.bntu.constructor.service.SettingsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@Validated
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SettingsServiceImpl implements SettingsService {

    private final SettingsRepository settingsRepository;
    private final ActionService actionService;

    @Override
    @Transactional
    public Settings saveSettings(@Valid Settings settings) {
        settings.setActions(
                actionService.saveAllActions(settings.getActions())
        );
        return settingsRepository.save(settings);
    }

    @Override
    @Transactional
    public List<Settings> saveAllSettings(@Valid List<Settings> settings) {
        settings.forEach(
                s -> s.setActions(actionService.saveAllActions(s.getActions()))
        );
        return settingsRepository.saveAll(settings);
    }

    @Override
    @Transactional
    public Settings addAction(@NotNull String settingsUuid, @Valid Action action) {
        Settings settings = findByUuid(settingsUuid);
        settings.getActions().add(actionService.saveAction(action));
//        settings.getActions().add(action);

        return settingsRepository.save(settings);
    }

    @Override
    @Transactional
    public Settings deleteAction(@NotNull String settingsUuid, @Valid String actionUuid) {
        Settings settings = findByUuid(settingsUuid);
        Action action = actionService.findActionByUuid(actionUuid);
        settings.getActions().remove(action);
        return settingsRepository.save(settings);
    }

    @Override
    public Settings findByUuid(@NotNull String settingsUuid) {
        Optional<Settings> settingsOptional = settingsRepository.findById(settingsUuid);
        return settingsOptional.orElse(null);
    }

    @Override
    @Transactional
    public Settings newSettings() {
        return settingsRepository.save(Settings.builder().build());
    }
}
