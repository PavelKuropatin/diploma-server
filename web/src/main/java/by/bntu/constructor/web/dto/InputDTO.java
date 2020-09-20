package by.bntu.constructor.web.dto;

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
public class InputDTO {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("connections")
    @Builder.Default
    private List<ConnectionDTO> connections = new LinkedList<>();

}
