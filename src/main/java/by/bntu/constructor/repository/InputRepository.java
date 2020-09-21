package by.bntu.constructor.repository;

import by.bntu.constructor.domain.Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputRepository extends JpaRepository<Input, String> {

}
