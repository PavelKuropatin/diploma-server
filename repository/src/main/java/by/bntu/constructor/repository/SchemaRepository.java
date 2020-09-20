package by.bntu.constructor.repository;

import by.bntu.constructor.domain.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SchemaRepository extends JpaRepository<Schema, String> {

    @Query("SELECT s " +
            "FROM Schema s " +
            "JOIN s.blocks b " +
            "WHERE b.uuid = :uuid")
    Schema findByBlockUuid(@Param("uuid") String blockUuid);

}
