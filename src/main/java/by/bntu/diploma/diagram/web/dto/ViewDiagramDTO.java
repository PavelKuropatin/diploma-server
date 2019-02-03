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
public class ViewDiagramDTO {

    @JsonProperty("uuid")
    private Long uuid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

}
