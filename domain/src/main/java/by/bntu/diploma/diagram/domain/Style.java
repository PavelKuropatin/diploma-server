package by.bntu.diploma.diagram.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "style")
public class Style {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uuid", nullable = false)
    private Long uuid;

    @Column(name = "source_style", nullable = false)
    @NotBlank(message = "{style.source-style.blank}")
    private String sourceStyle;

    @Column(name = "target_style", nullable = false)
    @NotBlank(message = "{style.target-style.blank}")
    private String targetStyle;

    @Column(name = "source_anchor_style", nullable = false)
    @NotBlank(message = "{style.source-anchor-style.blank}")
    private String sourceAnchorStyle;

    @Column(name = "target_anchor_style", nullable = false)
    @NotBlank(message = "{style.target-anchor-style.blank}")
    private String targetAnchorStyle;

}
