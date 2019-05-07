package by.bntu.diploma.diagram.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class VariableDTO {

    @JsonProperty("type")
    private String type;

    @JsonProperty("param")
    private String param;

    @JsonProperty("value")
    @Builder.Default
    private Double value = .0;

    @JsonProperty("function")
    private String function;
}
