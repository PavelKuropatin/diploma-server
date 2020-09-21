package by.bntu.constructor.domain;

import by.bntu.constructor.domain.constraint.util.ValidationMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Embeddable
public class Connection {

    @Valid
    @NotNull(message = ValidationMessage.Connection.INPUT_NULL)
    @ManyToOne(targetEntity = Input.class, cascade = CascadeType.ALL)
    private Input input;

    @Valid
    @NotNull(message = ValidationMessage.Connection.OUTPUT_NULL)
    @OneToOne(targetEntity = Output.class, cascade = CascadeType.ALL)
    private Output output;

    @Column(name = "is_visible", nullable = false)
    @Builder.Default
    private Boolean visible = true;

}
