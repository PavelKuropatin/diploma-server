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
public class ActionDTO {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("condition")
    private String condition;

    @JsonProperty("number")
    private String number;

    @JsonProperty("type")
    private String type;

    @JsonProperty("value")
    private String value;

}
