package by.bntu.constructor.domain;

import by.bntu.constructor.domain.constraint.util.ValidationMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "actions")
public class Action {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid", updatable = false, nullable = false)
    private String uuid;

    @Column(name = "condition", nullable = false)
    @Size(min = 3, max = 255, message = ValidationMessage.Action.CONDITION_SIZE)
    @NotBlank(message = ValidationMessage.Action.CONDITION_BLANK)
    private String condition;

    @Valid
    @NotNull(message = ValidationMessage.Action.NUMBER_NULL)
    private Integer number;

    @Column(name = "action_type", nullable = false)
    @Size(min = 1, max = 255, message = ValidationMessage.Action.TYPE_SIZE)
    @NotBlank(message = ValidationMessage.Action.TYPE_BLANK)
    private String type;

    @Column(name = "value")
    @Size(min = 1, max = 1000, message = ValidationMessage.Action.VALUE_SIZE)
//    @NotBlank(message = ValidationMessage.Action.VALUE_BLANK)
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Action action = (Action) o;

        return new EqualsBuilder()
                .append(uuid, action.uuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(uuid)
                .toHashCode();
    }
}
