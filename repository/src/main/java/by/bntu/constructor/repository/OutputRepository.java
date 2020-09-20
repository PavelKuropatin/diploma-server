package by.bntu.constructor.repository;

import by.bntu.constructor.domain.Output;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutputRepository extends JpaRepository<Output, String> {

}
