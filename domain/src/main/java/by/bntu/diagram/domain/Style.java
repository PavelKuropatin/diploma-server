package by.bntu.diagram.domain;

import by.bntu.diagram.domain.constraint.util.ValidationMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "style")
public class Style {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "uuid", updatable = false, nullable = false)

    private String uuid;

    @Column(name = "source_style", nullable = false)
    @Size(min = 3, max = 255, message = ValidationMessage.Style.SOURCE_STYLE_SIZE)
    @NotBlank(message = ValidationMessage.Style.SOURCE_STYLE_BLANK)
    private String sourceStyle;

    @Column(name = "target_style", nullable = false)
    @Size(min = 3, max = 255, message = ValidationMessage.Style.TARGET_STYLE_SIZE)
    @NotBlank(message = ValidationMessage.Style.TARGET_STYLE_BLANK)
    private String targetStyle;

    @Column(name = "source_anchor_style", nullable = false)
    @Size(min = 3, max = 255, message = ValidationMessage.Style.SOURCE_ANCHOR_STYLE_SIZE)
    @NotBlank(message = ValidationMessage.Style.SOURCE_ANCHOR_STYLE_BLANK)
    private String sourceAnchorStyle;

    @Column(name = "target_anchor_style", nullable = false)
    @Size(min = 3, max = 255, message = ValidationMessage.Style.TARGET_ANCHOR_STYLE_SIZE)
    @NotBlank(message = ValidationMessage.Style.TARGET_ANCHOR_STYLE_BLANK)
    private String targetAnchorStyle;

}
