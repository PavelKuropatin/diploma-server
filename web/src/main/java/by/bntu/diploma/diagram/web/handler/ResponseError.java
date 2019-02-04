package by.bntu.diploma.diagram.web.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseError {

    @JsonProperty("error")
    private Integer httpCode;


    @JsonProperty("message")
    private String message;

}
