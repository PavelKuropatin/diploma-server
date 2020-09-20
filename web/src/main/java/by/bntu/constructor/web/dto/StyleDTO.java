package by.bntu.constructor.web.dto;

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

    @JsonProperty("inputEndpoint")
    private String inputStyle;

    @JsonProperty("outputEndpoint")
    private String outputStyle;

    @JsonProperty("inputAnchor")
    private String inputAnchorStyle;

    @JsonProperty("outputAnchor")
    private String outputAnchorStyle;
}
