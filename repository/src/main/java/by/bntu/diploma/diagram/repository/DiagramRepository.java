package by.bntu.diploma.diagram.repository;

import by.bntu.diploma.diagram.domain.Diagram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagramRepository extends JpaRepository<Diagram, String> {

    @Query("SELECT d " +
            "FROM Diagram d " +
            "JOIN d.states s " +
            "WHERE s.uuid = :uuid")
    Diagram findByStateUuid(@Param("uuid") String stateUuid);

}
