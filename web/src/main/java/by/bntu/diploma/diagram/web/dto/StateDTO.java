package by.bntu.diploma.diagram.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StateDTO {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("color")
    private String color;

    @JsonProperty("template")
    private String template;

    @JsonProperty("endpointStyle")
    private StyleDTO style;

    @JsonProperty("sources")
    @Builder.Default
    private List<SourceDTO> sources = new LinkedList<>();

    @JsonProperty("targets")
    @Builder.Default
    private List<TargetDTO> targets = new LinkedList<>();

    @JsonProperty("inputContainer")
    @Builder.Default
    private List<Map<String, Object>> inputContainer = new LinkedList<>();

    @JsonProperty("outputContainer")
    @Builder.Default
    private List<Map<String, Object>> outputContainer = new LinkedList<>();

    @JsonProperty("x")
    private Integer positionX;

    @JsonProperty("y")
    private Integer positionY;

}
