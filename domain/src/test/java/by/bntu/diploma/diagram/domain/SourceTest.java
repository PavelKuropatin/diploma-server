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

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SourceTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("all valid")
    void validate_connectionsNull_expectedMessage() {
        Source source = Source.builder()
                .connections(null)
                .build();

        Set<ConstraintViolation<Source>> constraintViolations = validator.validate(source);

        List<String> expected = Collections.singletonList(ValidationMessage.Source.CONNECTIONS_NULL);
        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("connections valid")
    void validate_oneConnectionValid_expectedNoMessage() {
        Source source = Source.builder()
                .connections(Collections.singletonList(Connection.builder().target(new Target()).build()))
                .build();

        Set<ConstraintViolation<Source>> constraintViolations = validator.validate(source);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @DisplayName("connections invalid")
    void validate_oneConnectionInvalid_expectedNoMessage() {
        Source source = Source.builder()
                .connections(Collections.singletonList(Connection.builder().build()))
                .build();

        Set<ConstraintViolation<Source>> constraintViolations = validator.validate(source);

        List<String> expected = Collections.singletonList(ValidationMessage.Connection.TARGET_NULL);
        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

}