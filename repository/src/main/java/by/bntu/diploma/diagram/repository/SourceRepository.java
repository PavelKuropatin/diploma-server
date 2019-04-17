package by.bntu.diploma.diagram.repository;

import by.bntu.diploma.diagram.domain.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceRepository extends JpaRepository<Source, Long> {

}
