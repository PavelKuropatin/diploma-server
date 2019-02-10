package by.bntu.diploma.diagram.web.dto;

import by.bntu.diploma.diagram.domain.ContainerType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ContainerValueDTO {

    @JsonProperty("type")
    private ContainerType type;

    @JsonProperty("param")
    private String param;

    @JsonProperty("value")
    @Builder.Default
    private Double value = .0;

}
