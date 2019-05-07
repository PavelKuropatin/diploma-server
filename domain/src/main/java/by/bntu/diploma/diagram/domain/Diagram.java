package by.bntu.diploma.diagram.domain;

import by.bntu.diploma.diagram.domain.constraint.util.ValidationMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "diagram")
public class Diagram {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "uuid", updatable = false, nullable = false)

    private String uuid;

    @Column(name = "name", nullable = false)
    @Size(min = 3, max = 255, message = ValidationMessage.Diagram.NAME_SIZE)
    @NotBlank(message = ValidationMessage.Diagram.NAME_BLANK)
    private String name;

    @Column(name = "description", columnDefinition = "varchar(MAX)")
    @Size(min = 3, max = 255, message = ValidationMessage.Diagram.DESCRIPTION_SIZE)
    @NotBlank(message = ValidationMessage.Diagram.DESCRIPTION_BLANK)
    private String description;

    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "diagrams__states",
            joinColumns = @JoinColumn(name = "diagram_uuid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "state_uuid", nullable = false)
    )
    @NotNull(message = ValidationMessage.Diagram.STATES_NULL)
    @Builder.Default
    private List<State> states = new ArrayList<>();

    public void setStates(List<State> otherStates) {
        states.clear();
        if (otherStates != null) {
            states.addAll(otherStates);
        }
    }


}
