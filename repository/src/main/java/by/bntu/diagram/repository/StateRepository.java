package by.bntu.diagram.repository;

import by.bntu.diagram.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<State, String> {

    @Query("SELECT s " +
            "FROM State s " +
            "JOIN s.targets t " +
            "JOIN s.connections c " +
            "WHERE t.uuid = :uuid OR c.target.uuid = :uuid")
    List<State> findByTargetReference(@Param("uuid") String targetUuid);

    @Query("SELECT s " +
            "FROM State s " +
            "JOIN s.sources ss " +
            "JOIN s.connections c " +
            "WHERE ss.uuid = :uuid OR c.source.uuid = :uuid")
    List<State> findBySourceReference(@Param("uuid") String source);

}
