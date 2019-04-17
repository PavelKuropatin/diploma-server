package by.bntu.diploma.diagram.repository;

import by.bntu.diploma.diagram.domain.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StyleRepository extends JpaRepository<Style, Long> {

}
