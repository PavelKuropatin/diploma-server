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
public class EndpointStyleDTO {

    @JsonProperty("uuid")
    private Long uuid;

    @JsonProperty("sourceEndpoint")
    private String sourceEndpointStyle;

    @JsonProperty("targetEndpoint")
    private String targetEndpointStyle;

    @JsonProperty("sourceAnchor")
    private String sourceAnchorStyle;

    @JsonProperty("targetAnchor")
    private String targetAnchorStyle;
}
