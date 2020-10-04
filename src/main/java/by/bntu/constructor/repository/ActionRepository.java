package by.bntu.constructor.repository;

import by.bntu.constructor.domain.Action;
import by.bntu.constructor.domain.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<Action, String> {

}
