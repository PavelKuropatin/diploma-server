package by.bntu.diagram.domain;

import by.bntu.diagram.domain.constraint.NullOrMin;
import by.bntu.diagram.domain.constraint.util.ValidationMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "state")
public class State {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "uuid", updatable = false, nullable = false)
    private String uuid;

    @Column(name = "name", nullable = false)
    @Size(min = 1, max = 255, message = ValidationMessage.State.NAME_SIZE)
    @NotBlank(message = ValidationMessage.State.NAME_BLANK)
    private String name;

    @Column(name = "template", nullable = false)
    @Size(min = 1, max = 255, message = ValidationMessage.State.TEMPLATE_SIZE)
    @NotBlank(message = ValidationMessage.State.TEMPLATE_BLANK)
    private String template;

    @Column(name = "color", nullable = false)
    @Size(min = 1, max = 255, message = ValidationMessage.State.COLOR_SIZE)
    @NotBlank(message = ValidationMessage.State.COLOR_BLANK)
    private String color;

    @Valid
    @OneToOne
    @NotNull(message = ValidationMessage.State.STYLE_NULL)
    private Style style;

    @Column(name = "position_x", nullable = false)
    @NotNull(message = ValidationMessage.State.POS_X_NULL)
    private Double positionX;

    @Column(name = "position_y", nullable = false)
    @NotNull(message = ValidationMessage.State.POS_Y_NULL)
    private Double positionY;

    @NullOrMin
    @Column(name = "model_position")
    private Integer modelPosition;

    @Valid
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinTable(name = "state__source",
            joinColumns = @JoinColumn(name = "state_uuid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "source_uuid", nullable = false)
    )
    @NotNull(message = ValidationMessage.State.SOURCES_NULL)
    @Builder.Default
    private List<Source> sources = new LinkedList<>();

    @Valid
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinTable(name = "state__target",
            joinColumns = @JoinColumn(name = "state_uuid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "target_uuid", nullable = false)
    )
    @NotNull(message = ValidationMessage.State.TARGETS_NULL)
    @Builder.Default
    private List<Target> targets = new LinkedList<>();

    @Valid
    @NotNull(message = ValidationMessage.State.OC_NULL)
    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "connection",
            uniqueConstraints = {@UniqueConstraint(columnNames = {"state_uuid", "target_uuid", "source_uuid"})})
    private List<Connection> connections = new LinkedList<>();

    @Valid
    @NotNull(message = ValidationMessage.State.IC_NULL)
    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "state__variable")
    @Where(clause = "_type = 'INPUT'")
    private List<Variable> inputContainer = new LinkedList<>();

    @Valid
    @NotNull(message = ValidationMessage.State.CONNECTIONS_NULL)
    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "state__variable")
    @Where(clause = "_type = 'OUTPUT'")
    private List<Variable> outputContainer = new LinkedList<>();

    public void setInputContainer(List<Variable> otherInputContainer) {
        inputContainer.clear();
        if (otherInputContainer != null) {
            inputContainer.addAll(otherInputContainer);
        }
    }

    public void setOutputContainer(List<Variable> otherOutputContainer) {
        outputContainer.clear();
        if (otherOutputContainer != null) {
            outputContainer.addAll(otherOutputContainer);
        }
    }

    public void setSources(List<Source> otherSources) {
        sources.clear();
        if (otherSources != null) {
            sources.addAll(otherSources);
        }
    }

    public void setTargets(List<Target> otherTargets) {
        targets.clear();
        if (otherTargets != null) {
            targets.addAll(otherTargets);
        }
    }


    public void setConnections(List<Connection> otherConnections) {
        connections.clear();
        if (otherConnections != null) {
            connections.addAll(otherConnections);
        }
    }

}
