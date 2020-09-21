package by.bntu.constructor.web.dto;

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
public class BlockDTO {

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

    @JsonProperty("inputs")
    @Builder.Default
    private List<InputDTO> inputs = new LinkedList<>();

    @JsonProperty("outputs")
    @Builder.Default
    private List<OutputDTO> outputs = new LinkedList<>();

    @JsonProperty("inputVars")
    @Builder.Default
    private List<Map<String, Object>> inputVars = new LinkedList<>();

    @JsonProperty("outputVars")
    @Builder.Default
    private List<Map<String, Object>> outputVars = new LinkedList<>();

    @JsonProperty("x")
    private Integer positionX;

    @JsonProperty("y")
    private Double positionY;

    @JsonProperty("targetId")
    private Double modelPosition;

}
