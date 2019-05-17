package by.bntu.diploma.diagram.domain;

import by.bntu.diploma.diagram.domain.constraint.NullOrSize;
import by.bntu.diploma.diagram.domain.constraint.util.ValidationMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Embeddable
public class Variable {

    @NotBlank(message = ValidationMessage.Variable.PARAM_BLANK)
    @Column(name = "param", nullable = false)
    @Size(min = 1, max = 30, message = ValidationMessage.Variable.PARAM_SIZE)
    private String param;

    @NotNull(message = ValidationMessage.Variable.VALUE_NULL)
    @Column(name = "value", nullable = false)
    private Double value;

    @NullOrSize(min = 1, max = 255, message = ValidationMessage.Variable.FUNC_NULL_OR_SIZE)
    @Column(name = "_function")
    private String function;

    @NotNull(message = ValidationMessage.Variable.TYPE_NULL)
    @Enumerated(EnumType.STRING)
    @Column(name = "_type")
    private Type type;

    public enum Type {
        INPUT, OUTPUT
    }
}
