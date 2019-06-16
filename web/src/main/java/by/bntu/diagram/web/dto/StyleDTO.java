package by.bntu.diagram.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StyleDTO {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("sourceEndpoint")
    private String sourceStyle;

    @JsonProperty("targetEndpoint")
    private String targetStyle;

    @JsonProperty("sourceAnchor")
    private String sourceAnchorStyle;

    @JsonProperty("targetAnchor")
    private String targetAnchorStyle;
}
