package by.bntu.diploma.diagram.repository;

import by.bntu.diploma.diagram.domain.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetRepository extends JpaRepository<Target, String> {

}
