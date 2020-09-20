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
public class ConnectionDTO {

    @JsonProperty("uuid")
    private String outputUuid;

    @JsonProperty("is_visible")
    private Boolean visible;

}
