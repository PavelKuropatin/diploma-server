package by.bntu.constructor.domain;

import by.bntu.constructor.domain.constraint.NullOrMin;
import by.bntu.constructor.domain.constraint.util.ValidationMessage;
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
@Table(name = "blocks")
public class Block {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "uuid", updatable = false, nullable = false)
    private String uuid;

    @Column(name = "name", nullable = false)
    @Size(min = 3, max = 255, message = ValidationMessage.Block.NAME_SIZE)
    @NotBlank(message = ValidationMessage.Block.NAME_BLANK)
    private String name;

    @Column(name = "template", nullable = false)
    @Size(min = 3, max = 255, message = ValidationMessage.Block.TEMPLATE_SIZE)
    @NotBlank(message = ValidationMessage.Block.TEMPLATE_BLANK)
    private String template;

    @Column(name = "color", nullable = false)
    @Size(min = 3, max = 255, message = ValidationMessage.Block.COLOR_SIZE)
    @NotBlank(message = ValidationMessage.Block.COLOR_BLANK)
    private String color;

    @Valid
    @OneToOne
    @NotNull(message = ValidationMessage.Block.STYLE_NULL)
    private Style style;

    @Column(name = "position_x", nullable = false)
    @NotNull(message = ValidationMessage.Block.POS_X_NULL)
    private Double positionX;

    @Column(name = "position_y", nullable = false)
    @NotNull(message = ValidationMessage.Block.POS_Y_NULL)
    private Double positionY;

    @NullOrMin
    @Column(name = "model_position")
    private Integer modelPosition;

    @Valid
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinTable(name = "blocks__inputs",
            joinColumns = @JoinColumn(name = "block_uuid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "input_uuid", nullable = false)
    )
    @NotNull(message = ValidationMessage.Block.INPUTS_NULL)
    @Builder.Default
    private List<Input> inputs = new LinkedList<>();

    @Valid
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinTable(name = "blocks__outputs",
            joinColumns = @JoinColumn(name = "block_uuid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "output_uuid", nullable = false)
    )
    @NotNull(message = ValidationMessage.Block.OUTPUTS_NULL)
    @Builder.Default
    private List<Output> outputs = new LinkedList<>();

    @Valid
    @NotNull(message = ValidationMessage.Block.OUTPUT_VARS_NULL)
    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "connections",
            uniqueConstraints = {@UniqueConstraint(columnNames = {"block_uuid", "output_uuid", "input_uuid"})})
    private List<Connection> connections = new LinkedList<>();

    @Valid
    @NotNull(message = ValidationMessage.Block.INPUT_VARS_NULL)
    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "blocks__variables")
    @Where(clause = "_type = 'INPUT'")
    private List<Variable> inputVars = new LinkedList<>();

    @Valid
    @NotNull(message = ValidationMessage.Block.CONNECTIONS_NULL)
    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "blocks__variables")
    @Where(clause = "_type = 'OUTPUT'")
    private List<Variable> outputVars = new LinkedList<>();

    public void setInputVars(List<Variable> otherInputVars) {
        inputVars.clear();
        if (otherInputVars != null) {
            inputVars.addAll(otherInputVars);
        }
    }

    public void setOutputVars(List<Variable> otherOutputVars) {
        outputVars.clear();
        if (otherOutputVars != null) {
            outputVars.addAll(otherOutputVars);
        }
    }

    public void setInputs(List<Input> otherInputs) {
        inputs.clear();
        if (otherInputs != null) {
            inputs.addAll(otherInputs);
        }
    }

    public void setOutputs(List<Output> otherOutputs) {
        outputs.clear();
        if (otherOutputs != null) {
            outputs.addAll(otherOutputs);
        }
    }


    public void setConnections(List<Connection> otherConnections) {
        connections.clear();
        if (otherConnections != null) {
            connections.addAll(otherConnections);
        }
    }

}
