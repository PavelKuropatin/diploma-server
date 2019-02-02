package by.bntu.diploma.diagram.entity;

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

    @NotBlank
    @Column(name = "source_style", nullable = false)
    private String sourceStyle;

    @NotBlank
    @Column(name = "target_style", nullable = false)
    private String targetStyle;

    @NotBlank
    @Column(name = "source_anchor_style", nullable = false)
    private String sourceAnchorStyle;

    @NotBlank
    @Column(name = "target_anchor_style", nullable = false)
    private String targetAnchorStyle;

}