package by.bntu.diploma.diagram.domain;

import by.bntu.diploma.diagram.domain.constraint.util.ValidationMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Embeddable
public class Variable {

    @NotBlank(message = ValidationMessage.Variable.PARAM_BLANK)
    @Column(name = "param", nullable = false)
    private String param;

    @NotNull(message = ValidationMessage.Variable.VALUE_NULL)
    @Column(name = "value", nullable = false)
    private Double value;

    @Column(name = "_function")
    private String function;
}
