package by.bntu.constructor.service;

import by.bntu.constructor.domain.Action;
import by.bntu.constructor.domain.Input;
import by.bntu.constructor.domain.Settings;
import by.bntu.constructor.domain.constraint.util.ValidationMessage;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface SettingsService {

    Settings newSettings();
    Settings findByUuid(
            @NotNull String settingsUuid
    );
    Settings saveSettings(@Valid Settings settings);
    List<Settings> saveAllSettings(@Valid List<Settings> settings);
    Settings addAction(@NotNull String settingsUuid,@Valid Action action);
    Settings deleteAction(@NotNull String settingsUuid,@Valid String actionUuid);

}
