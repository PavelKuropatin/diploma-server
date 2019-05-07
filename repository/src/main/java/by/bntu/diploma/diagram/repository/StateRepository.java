package by.bntu.diploma.diagram.repository;

import by.bntu.diploma.diagram.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, String> {

}
