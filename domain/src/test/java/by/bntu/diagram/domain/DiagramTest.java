package by.bntu.diagram.domain;

import by.bntu.diagram.domain.constraint.util.ValidationMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiagramTest {

    private static final String VALID_STR = RandomStringUtils.random(127, true, true);

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
                .positionX(RandomUtils.nextDouble())
                .positionY(RandomUtils.nextDouble())
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();
    }

    @Test
    @DisplayName("valid states")
    void validate_validStates_expectedNoMessage() {
        Diagram diagram = Diagram.builder()
                .name(VALID_STR)
                .description(VALID_STR)
                .states(Collections.singletonList(state))
                .build();

        Set<ConstraintViolation<Diagram>> constraintViolations = validator.validate(diagram);

        assertTrue(constraintViolations.isEmpty());
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

    @ParameterizedTest(name = "run #[{index}] {2}")
    @DisplayName("parametrized")
    @CsvFileSource(resources = "/diagram.csv", numLinesToSkip = 1)
    void validate__expectedMessage(String name, String description, String messages) {

        Diagram diagram = Diagram.builder()
                .name(name)
                .description(description)
                .build();


        List<String> actual = validator.validate(diagram).stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        String[] expected;
        if (StringUtils.isBlank(messages)) {
            expected = new String[0];
        } else {
            expected = messages.split(":");
        }

        assertEquals(expected.length, actual.size());
        assertThat(actual, hasItems(expected));
    }
}