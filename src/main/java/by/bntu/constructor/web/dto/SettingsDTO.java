package by.bntu.constructor.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SettingsDTO {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("actions")
    @Builder.Default
    private List<ActionDTO> actions = new LinkedList<>();

}
