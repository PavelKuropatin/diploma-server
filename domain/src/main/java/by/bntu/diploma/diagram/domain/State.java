package by.bntu.diploma.diagram.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "state")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uuid", nullable = false)
    private Long uuid;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "{state.name.blank}")
    private String name;

    @Column(name = "template", nullable = false)
    @NotBlank(message = "{state.template.blank}")
    private String template;

    @Column(name = "color", nullable = false)
    @NotBlank(message = "{state.color.blank}")
    private String color;

    @Valid
    @OneToOne
    @NotNull(message = "{style.null}")
    private Style style;

    @Column(name = "position_x", nullable = false)
    @NotNull(message = "{state.position-x.null}")
    private Integer positionX;

    @Column(name = "position_y", nullable = false)
    @NotNull(message = "{state.position-y.null}")
    private Integer positionY;

    @Valid
    @OneToMany
    @JoinTable(name = "states__sources",
            joinColumns = @JoinColumn(name = "state_uuid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "source_uuid", nullable = false)
    )
    @NotNull(message = "{state.sources.null}")
    @Builder.Default
    private List<Source> sources = new LinkedList<>();

    @Valid
    @OneToMany
    @JoinTable(name = "states__targets",
            joinColumns = @JoinColumn(name = "state_uuid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "target_uuid", nullable = false)
    )
    @NotNull(message = "{state.targets.null}")
    @Builder.Default
    private List<Target> targets = new LinkedList<>();

    @ElementCollection
    @MapKeyColumn(name = "variable_name")
    @Column(name = "variable_value", nullable = false)
    @CollectionTable(name = "input_container", joinColumns = @JoinColumn(name = "state_uuid"))
    @NotNull(message = "{state.input-container.null}")
    @Builder.Default
    private Map<String, Double> inputContainer = new LinkedHashMap<>();

    @ElementCollection
    @MapKeyColumn(name = "variable_name")
    @Column(name = "variable_value", nullable = false)
    @CollectionTable(name = "output_container", joinColumns = @JoinColumn(name = "state_uuid"))
    @NotNull(message = "{state.output-container.null}")
    @Builder.Default
    private Map<String, Double> outputContainer = new LinkedHashMap<>();

    public void setInputContainer(Map<String, Double> inputContainer) {
        this.inputContainer.clear();
        if (inputContainer != null) {
            this.inputContainer.putAll(inputContainer);
        }
    }

    public void setOutputContainer(Map<String, Double> outputContainer) {
        this.outputContainer.clear();
        if (outputContainer != null) {
            this.outputContainer.putAll(outputContainer);
        }
    }

    public void setSources(List<Source> sources) {
        this.sources.clear();
        if (sources != null) {
            this.sources.addAll(sources);
        }
    }

    public void setTargets(List<Target> targets) {
        this.targets.clear();
        if (targets != null) {
            this.targets.addAll(targets);
        }
    }

}
