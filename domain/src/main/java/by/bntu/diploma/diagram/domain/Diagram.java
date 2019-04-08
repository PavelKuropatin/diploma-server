package by.bntu.diploma.diagram.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uuid", nullable = false)
    private Long uuid;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "{diagram.name.blank}")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @NotBlank(message = "{diagram.description.blank}")
    private String description;

    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "diagrams__states",
            joinColumns = @JoinColumn(name = "diagram_uuid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "state_uuid", nullable = false)
    )
    @NotNull(message = "{diagram.states.null}")
    @Builder.Default
    private List<State> states = new ArrayList<>();

    public void setStates(List<State> states) {
        if (states != null) {
            this.states.clear();
            this.states.addAll(states);
        }
    }

}
