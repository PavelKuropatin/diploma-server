package by.bntu.diploma.diagram.repository;

import by.bntu.diploma.diagram.domain.Diagram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagramRepository extends JpaRepository<Diagram, Long> {

}
