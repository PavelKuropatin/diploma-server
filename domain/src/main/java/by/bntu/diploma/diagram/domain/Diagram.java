package by.bntu.diploma.diagram.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "diagrams__states",
            joinColumns = @JoinColumn(name = "diagram_uuid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "state_uuid", nullable = false)
    )
    @Builder.Default
    private List<State> states = new ArrayList<>();

    public void setStates(List<State> states) {
        if (states != null) {
            this.states.clear();
            this.states.addAll(states);
        }
    }

}
