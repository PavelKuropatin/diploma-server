package by.bntu.diploma.diagram.domain;

import by.bntu.diploma.diagram.domain.constraint.util.ValidationMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConnectionTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("target null")
    void validate_targetObjectNull_expectedMessage() {
        Connection connection = Connection.builder()
                .target(null)
                .build();

        Set<ConstraintViolation<Connection>> constraintViolations = validator.validate(connection);
        List<String> expected = Collections.singletonList(ValidationMessage.Connection.TARGET_NULL);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("target not null")
    void validate_targetObjectNotNull_expectedNoMessage() {
        Connection connection = Connection.builder()
                .target(new Target())
                .build();

        Set<ConstraintViolation<Connection>> constraintViolations = validator.validate(connection);

        assertTrue(constraintViolations.isEmpty());
    }

}