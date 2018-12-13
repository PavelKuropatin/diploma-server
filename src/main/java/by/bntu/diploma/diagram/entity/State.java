package by.bntu.diploma.diagram.entity;

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
    private EndpointStyle endpointStyle;

    @NotNull
    @Column(name = "position_y", nullable = false)
    private Integer positionY;

    @Valid
    @OneToMany
    @JoinTable(name = "states__source_endpoints",
            joinColumns = @JoinColumn(name = "state_uuid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "source_endpoint_uuid", nullable = false)
    )
    @Builder.Default
    private List<SourceEndpoint> sourceEndpoints = new LinkedList<>();

    @Valid
    @OneToMany
    @JoinTable(name = "states__target_endpoints",
            joinColumns = @JoinColumn(name = "state_uuid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "target_endpoint_uuid", nullable = false)
    )
    @Builder.Default
    private List<TargetEndpoint> targetEndpoints = new LinkedList<>();

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

    public void setSourceEndpoints(List<SourceEndpoint> sourceEndpoints) {
        this.sourceEndpoints.clear();
        if (sourceEndpoints != null) {
            this.sourceEndpoints.addAll(sourceEndpoints);
        }
    }

    public void setTargetEndpoints(List<TargetEndpoint> targetEndpoints) {
        this.targetEndpoints.clear();
        if (targetEndpoints != null) {
            this.targetEndpoints.addAll(targetEndpoints);
        }
    }

}
