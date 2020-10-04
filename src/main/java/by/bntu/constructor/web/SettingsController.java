package by.bntu.constructor.web;

import by.bntu.constructor.domain.Action;
import by.bntu.constructor.domain.Settings;
import by.bntu.constructor.service.ActionService;
import by.bntu.constructor.service.SettingsService;
import by.bntu.constructor.web.dto.ActionDTO;
import by.bntu.constructor.web.dto.SettingsDTO;
import by.bntu.constructor.web.dto.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settings")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SettingsController {

    private final SettingsService settingsService;
    private final ActionService actionService;

    private final Mapper<Settings, SettingsDTO> settingsMapper;
    private final Mapper<Action, ActionDTO> actionMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{settings_uuid}/action")
    public SettingsDTO addAction(@PathVariable(name = "settings_uuid") String settingsUuid,
                                 @RequestBody ActionDTO actionDTO) {
        Action action = actionMapper.fromDTO(actionDTO);
        Settings settings = settingsService.addAction(settingsUuid, action);
        return settingsMapper.toDTO(settings);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(value = "/{settings_uuid}/action/{action_uuid}")
    public SettingsDTO deleteAction(@PathVariable(name = "settings_uuid") String settingsUuid,
                                    @PathVariable(name = "action_uuid") String actionUuid) {
        Settings settings = settingsService.deleteAction(settingsUuid, actionUuid);
        return settingsMapper.toDTO(settings);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{settings_uuid}")
    public SettingsDTO saveSettings(@PathVariable(name = "settings_uuid") String settingsUuid,
                                    @RequestBody SettingsDTO settingsDTO) {
        Settings settings = settingsService.findByUuid(settingsUuid);
        List<Action> actions = settingsMapper.fromDTO(settingsDTO).getActions();
        actions = actionService.saveAllActions(actions);
        settings.setActions(actions);
        settings = settingsService.saveSettings(settings);
        return settingsMapper.toDTO(settings);
    }

}
