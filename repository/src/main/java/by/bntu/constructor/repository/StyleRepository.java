package by.bntu.constructor.repository;

import by.bntu.constructor.domain.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StyleRepository extends JpaRepository<Style, String> {

}
