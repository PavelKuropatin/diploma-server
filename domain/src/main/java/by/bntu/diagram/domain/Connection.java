package by.bntu.diagram.domain;

import by.bntu.diagram.domain.constraint.util.ValidationMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Embeddable
public class Connection {

    @Valid
    @NotNull(message = ValidationMessage.Connection.SOURCE_NULL)
    @ManyToOne(targetEntity = Source.class, cascade = CascadeType.ALL)
    private Source source;

    @Valid
    @NotNull(message = ValidationMessage.Connection.TARGET_NULL)
    @OneToOne(targetEntity = Target.class, cascade = CascadeType.ALL)
    private Target target;

}