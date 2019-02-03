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

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "template", nullable = false)
    private String template;

    @NotBlank
    @Column(name = "color", nullable = false)
    private String color;

    @NotNull
    @Column(name = "position_x", nullable = false)
    private Integer positionX;

    @Valid
    @OneToOne
    private Style style;

    @NotNull
    @Column(name = "position_y", nullable = false)
    private Integer positionY;

    @Valid
    @OneToMany
    @JoinTable(name = "states__sources",
            joinColumns = @JoinColumn(name = "state_uuid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "source_uuid", nullable = false)
    )
    @Builder.Default
    private List<Source> sources = new LinkedList<>();

    @Valid
    @OneToMany
    @JoinTable(name = "states__targets",
            joinColumns = @JoinColumn(name = "state_uuid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "target_uuid", nullable = false)
    )
    @Builder.Default
    private List<Target> targets = new LinkedList<>();

    @ElementCollection
    @MapKeyColumn(name = "variable_name")
    @Column(name = "variable_value", nullable = false)
    @CollectionTable(name = "input_container", joinColumns = @JoinColumn(name = "state_uuid"))
    @Builder.Default
    private Map<String, Double> inputContainer = new LinkedHashMap<>();

    @ElementCollection
    @MapKeyColumn(name = "variable_name")
    @Column(name = "variable_value", nullable = false)
    @CollectionTable(name = "output_container", joinColumns = @JoinColumn(name = "state_uuid"))
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
