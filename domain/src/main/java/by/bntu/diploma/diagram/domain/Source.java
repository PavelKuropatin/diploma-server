package by.bntu.diploma.diagram.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "source")
public class Source {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uuid", nullable = false)
    private Long uuid;

    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "sources__connections",
            joinColumns = @JoinColumn(name = "source_uuid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "connection_uuid", nullable = false)
    )
    @Builder.Default
    private List<Connection> connections = new LinkedList<>();

    public void setConnections(List<Connection> connections) {
        if (connections != null) {
            this.connections.clear();
            this.connections.addAll(connections);
        }
    }
}