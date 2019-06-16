package by.bntu.diagram.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DiagramDTO {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("modules")
    @Builder.Default
    private List<StateDTO> states = new LinkedList<>();

}