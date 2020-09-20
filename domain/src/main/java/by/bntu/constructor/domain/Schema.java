package by.bntu.constructor.domain;

import by.bntu.constructor.domain.constraint.util.ValidationMessage;
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
@Table(name = "schemas")
public class Schema {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid", updatable = false, nullable = false)

    private String uuid;

    @Column(name = "name", nullable = false)
    @Size(min = 3, max = 255, message = ValidationMessage.Schema.NAME_SIZE)
    @NotBlank(message = ValidationMessage.Schema.NAME_BLANK)
    private String name;

    @Column(name = "description", nullable = false)
    @Size(min = 3, max = 255, message = ValidationMessage.Schema.DESCRIPTION_SIZE)
    @NotBlank(message = ValidationMessage.Schema.DESCRIPTION_BLANK)
    private String description;

    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "schemas__blocks",
            joinColumns = @JoinColumn(name = "schema_uuid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "block_uuid", nullable = false)
    )
    @NotNull(message = ValidationMessage.Schema.BLOCKS_NULL)
    @Builder.Default
    private List<Block> blocks = new ArrayList<>();

    public void setBlocks(List<Block> otherBlocks) {
        blocks.clear();
        if (otherBlocks != null) {
            blocks.addAll(otherBlocks);
        }
    }
}
