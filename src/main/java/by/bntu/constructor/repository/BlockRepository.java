package by.bntu.constructor.repository;

import by.bntu.constructor.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block, String> {

    @Query("SELECT b " +
            "FROM Block b " +
            "JOIN b.outputs o " +
            "JOIN b.connections c " +
            "WHERE o.uuid = :uuid OR c.output.uuid = :uuid")
    List<Block> findByOutputUuid(@Param("uuid") String outputUuid);

    @Query("SELECT b " +
            "FROM Block b " +
            "JOIN b.inputs i " +
            "JOIN b.connections c " +
            "WHERE i.uuid = :uuid OR c.input.uuid = :uuid")
    List<Block> findByInputUuid(@Param("uuid") String inputUuid);

}
