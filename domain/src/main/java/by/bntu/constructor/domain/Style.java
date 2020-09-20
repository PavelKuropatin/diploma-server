package by.bntu.constructor.domain;

import by.bntu.constructor.domain.constraint.util.ValidationMessage;
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

    @Column(name = "input_style", nullable = false)
    @Size(min = 3, max = 255, message = ValidationMessage.Style.INPUT_STYLE_SIZE)
    @NotBlank(message = ValidationMessage.Style.INPUT_STYLE_BLANK)
    private String inputStyle;

    @Column(name = "output_style", nullable = false)
    @Size(min = 3, max = 255, message = ValidationMessage.Style.OUTPUT_STYLE_SIZE)
    @NotBlank(message = ValidationMessage.Style.OUTPUT_STYLE_BLANK)
    private String outputStyle;

    @Column(name = "input_anchor_style", nullable = false)
    @Size(min = 3, max = 255, message = ValidationMessage.Style.INPUT_ANCHOR_STYLE_SIZE)
    @NotBlank(message = ValidationMessage.Style.INPUT_ANCHOR_STYLE_BLANK)
    private String inputAnchorStyle;

    @Column(name = "output_anchor_style", nullable = false)
    @Size(min = 3, max = 255, message = ValidationMessage.Style.OUTPUT_ANCHOR_STYLE_SIZE)
    @NotBlank(message = ValidationMessage.Style.OUTPUT_ANCHOR_STYLE_BLANK)
    private String outputAnchorStyle;

}
