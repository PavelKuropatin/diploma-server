package by.bntu.diploma.diagram.domain;

import by.bntu.diploma.diagram.domain.constraint.util.ValidationMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiagramTest {

    private static final String VALID_STR = RandomStringUtils.random(127, true, true);
    private static final String INVALID_STR_256 = RandomStringUtils.random(256, true, true);
    private static final String INVALID_STR_2 = RandomStringUtils.random(2, true, true);

    private static Validator validator;
    private static State state;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        state = State.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(RandomUtils.nextInt())
                .positionY(RandomUtils.nextInt())
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();
    }

    @Test
    @DisplayName("all valid")
    void validate_allValid_expectedNoMessage() {
        Diagram diagram = Diagram.builder()
                .name(VALID_STR)
                .description(VALID_STR)
                .states(Collections.singletonList(state))
                .build();

        Set<ConstraintViolation<Diagram>> constraintViolations = validator.validate(diagram);

        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @DisplayName("name null")
    void validate_nameNull_expectedMessage() {
        Diagram diagram = Diagram.builder()
                .name(null)
                .description(VALID_STR)
                .states(Collections.singletonList(state))
                .build();

        Set<ConstraintViolation<Diagram>> constraintViolations = validator.validate(diagram);
        List<String> expected = Collections.singletonList(ValidationMessage.Diagram.NAME_BLANK);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("name blank")
    void validate_nameBlank_expectedMessage() {
        Diagram diagram = Diagram.builder()
                .name(StringUtils.SPACE)
                .description(VALID_STR)
                .states(Collections.singletonList(state))
                .build();

        Set<ConstraintViolation<Diagram>> constraintViolations = validator.validate(diagram);
        List<String> expected = Stream.of(ValidationMessage.Diagram.NAME_BLANK, ValidationMessage.Diagram.NAME_SIZE)
                .collect(Collectors.toList());

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("name less min size")
    void validate_nameLessMinSize_expectedMessage() {
        Diagram diagram = Diagram.builder()
                .name(INVALID_STR_2)
                .description(VALID_STR)
                .states(Collections.singletonList(state))
                .build();

        Set<ConstraintViolation<Diagram>> constraintViolations = validator.validate(diagram);
        List<String> expected = Collections.singletonList(ValidationMessage.Diagram.NAME_SIZE);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("name more max size")
    void validate_nameMoreMaxSize_expectedMessage() {
        Diagram diagram = Diagram.builder()
                .name(INVALID_STR_256)
                .description(VALID_STR)
                .states(Collections.singletonList(state))
                .build();

        Set<ConstraintViolation<Diagram>> constraintViolations = validator.validate(diagram);
        List<String> expected = Collections.singletonList(ValidationMessage.Diagram.NAME_SIZE);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("description null")
    void validate_descriptionNull_expectedMessage() {
        Diagram diagram = Diagram.builder()
                .name(VALID_STR)
                .description(null)
                .states(Collections.singletonList(state))
                .build();

        Set<ConstraintViolation<Diagram>> constraintViolations = validator.validate(diagram);
        List<String> expected = Collections.singletonList(ValidationMessage.Diagram.DESCRIPTION_BLANK);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("description blank")
    void validate_descriptionBlank_expectedMessage() {
        Diagram diagram = Diagram.builder()
                .name(VALID_STR)
                .description(StringUtils.SPACE)
                .states(Collections.singletonList(state))
                .build();

        Set<ConstraintViolation<Diagram>> constraintViolations = validator.validate(diagram);
        List<String> expected = Stream.of(ValidationMessage.Diagram.DESCRIPTION_BLANK, ValidationMessage.Diagram.DESCRIPTION_SIZE)
                .collect(Collectors.toList());

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("description less min size")
    void validate_descriptionLessMinSize_expectedMessage() {
        Diagram diagram = Diagram.builder()
                .name(VALID_STR)
                .description(INVALID_STR_2)
                .states(Collections.singletonList(state))
                .build();

        Set<ConstraintViolation<Diagram>> constraintViolations = validator.validate(diagram);
        List<String> expected = Collections.singletonList(ValidationMessage.Diagram.DESCRIPTION_SIZE);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("description more max size")
    void validate_descriptionMoreMaxSize_expectedMessage() {
        Diagram diagram = Diagram.builder()
                .description(INVALID_STR_256)
                .name(VALID_STR)
                .states(Collections.singletonList(state))
                .build();

        Set<ConstraintViolation<Diagram>> constraintViolations = validator.validate(diagram);
        List<String> expected = Collections.singletonList(ValidationMessage.Diagram.DESCRIPTION_SIZE);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("states null")
    void validate_statesNull_expectedMessage() {
        Diagram diagram = Diagram.builder()
                .name(VALID_STR)
                .description(VALID_STR)
                .states(null)
                .build();

        Set<ConstraintViolation<Diagram>> constraintViolations = validator.validate(diagram);
        List<String> expected = Collections.singletonList(ValidationMessage.Diagram.STATES_NULL);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }
}