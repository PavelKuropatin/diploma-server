package by.bntu.diagram.repository;

import by.bntu.diagram.domain.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetRepository extends JpaRepository<Target, String> {

}
