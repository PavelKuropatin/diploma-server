package by.bntu.diploma.diagram.domain;

import by.bntu.diploma.diagram.domain.constraint.util.ValidationMessage;
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
@Entity
@Table(name = "connection")
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uuid", nullable = false)
    private Long uuid;

    @Valid
    @NotNull(message = ValidationMessage.Connection.TARGET_NULL)
    @ManyToOne(targetEntity = Target.class)
    private Target target;

}