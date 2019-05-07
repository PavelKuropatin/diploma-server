package by.bntu.diploma.diagram.repository;

import by.bntu.diploma.diagram.domain.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, String> {

}