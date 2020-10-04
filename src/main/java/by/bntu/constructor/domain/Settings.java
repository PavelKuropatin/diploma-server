package by.bntu.constructor.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "settings")
public class Settings {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid", updatable = false, nullable = false)
    private String uuid;

    @Valid
    @Builder.Default
    @OrderBy("number")
//    @Cascade(value = {CascadeType.ALL})
    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    private List<Action> actions = new LinkedList<>();

    public void setActions(List<Action> otherActions) {
        if (otherActions != null) {
            actions.clear();
            actions.addAll(otherActions);
        }
    }
}