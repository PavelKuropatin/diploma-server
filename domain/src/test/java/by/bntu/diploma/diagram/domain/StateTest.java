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
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StateTest {

    private static final String VALID_STR = RandomStringUtils.random(127, true, true);
    private static final String INVALID_STR_256 = RandomStringUtils.random(256, true, true);
    private static final String INVALID_STR_2 = RandomStringUtils.random(2, true, true);
    private static final Integer VALID_INT = RandomUtils.nextInt();
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("all valid")
    void validate_allValid_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);

        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    @DisplayName("name null")
    void validate_nameNull_expectedMessage() {
        State state = State.builder()
                .name(null)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);
        List<String> expected = Collections.singletonList(ValidationMessage.State.NAME_BLANK);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("name blank")
    void validate_nameBlank_expectedMessage() {
        State state = State.builder()
                .name(StringUtils.SPACE)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);
        List<String> expected = Stream.of(ValidationMessage.State.NAME_BLANK, ValidationMessage.State.NAME_SIZE)
                .sorted()
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
        State state = State.builder()
                .name(INVALID_STR_2)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);
        List<String> expected = Stream.of(ValidationMessage.State.NAME_SIZE)
                .collect(Collectors.toList());

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("name more max size")
    void validate_nameMoreMaxSize_expectedMessage() {
        State state = State.builder()
                .name(INVALID_STR_256)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);
        List<String> expected = Stream.of(ValidationMessage.State.NAME_SIZE)
                .collect(Collectors.toList());

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("color null")
    void validate_colorNull_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(null)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);

        List<String> expected = Collections.singletonList(ValidationMessage.State.COLOR_BLANK);
        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("color blank")
    void validate_colorBlank_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(StringUtils.SPACE)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);
        List<String> expected = Stream.of(ValidationMessage.State.COLOR_BLANK, ValidationMessage.State.COLOR_SIZE)
                .sorted()
                .collect(Collectors.toList());

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("color less min size")
    void validate_colorLessMinSize_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(INVALID_STR_2)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);
        List<String> expected = Collections.singletonList(ValidationMessage.State.COLOR_SIZE);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("color more max size")
    void validate_colorMoreMaxSize_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(INVALID_STR_256)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);
        List<String> expected = Collections.singletonList(ValidationMessage.State.COLOR_SIZE);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("template null")
    void validate_templateNull_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(null)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);
        List<String> expected = Collections.singletonList(ValidationMessage.State.TEMPLATE_BLANK);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("template blank")
    void validate_templateBlank_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(StringUtils.SPACE)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);
        List<String> expected = Stream.of(ValidationMessage.State.TEMPLATE_BLANK, ValidationMessage.State.TEMPLATE_SIZE)
                .sorted()
                .collect(Collectors.toList());

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("template less min size")
    void validate_templateLessMinSize_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(INVALID_STR_2)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);
        List<String> expected = Collections.singletonList(ValidationMessage.State.TEMPLATE_SIZE);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("template more max size")
    void validate_templateMoreMaxSize_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(INVALID_STR_256)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);
        List<String> expected = Collections.singletonList(ValidationMessage.State.TEMPLATE_SIZE);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("positionX null")
    void validate_positionXNull_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(null)
                .positionY(VALID_INT)
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);
        List<String> expected = Collections.singletonList(ValidationMessage.State.POS_X_NULL);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("positionY null")
    void validate_positionYNull_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(null)
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);
        List<String> expected = Collections.singletonList(ValidationMessage.State.POS_Y_NULL);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("style null")
    void validate_styleNull_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .style(null)
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);

        List<String> expected = Collections.singletonList(ValidationMessage.State.STYLE_NULL);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("sources null")
    void validate_sourcesNull_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .sources(null)
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);

        List<String> expected = Collections.singletonList(ValidationMessage.State.SOURCES_NULL);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("targets null")
    void validate_targetsNull_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .targets(null)
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);

        List<String> expected = Collections.singletonList(ValidationMessage.State.TARGETS_NULL);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("inputContainer null")
    void validate_inputContainerNull_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .inputContainer(null)
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);

        List<String> expected = Collections.singletonList(ValidationMessage.State.IC_NULL);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("inputContainer contains empty key")
    void validate_inputContainerContainsEmptyKey_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .inputContainer(new HashMap<String, Double>() {
                    {
                        put("", .1);
                        put("key2", .1);
                    }
                })
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);

        List<String> expected = Collections.singletonList(ValidationMessage.State.IC_VALUES + "[<blank> : 0.1]");

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("inputContainer contains null key")
    void validate_inputContainerContainsNullKey_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .inputContainer(new HashMap<String, Double>() {
                    {
                        put(null, .1);
                        put("key2", .1);
                    }
                })
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);

        List<String> expected = Collections.singletonList(ValidationMessage.State.IC_VALUES + "[<null> : 0.1]");

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("inputContainer contains null value")
    void validate_inputContainerContainsNullValue_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .inputContainer(new HashMap<String, Double>() {
                    {
                        put("key1", null);
                        put("key2", .1);
                    }
                })
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);

        List<String> expected = Collections.singletonList(ValidationMessage.State.IC_VALUES + "[key1 : <null>]");

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("outputContainer null")
    void validate_outputContainerNull_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .outputContainer(null)
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);

        List<String> expected = Collections.singletonList(ValidationMessage.State.OC_NULL);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("outputContainer contains empty key")
    void validate_outputContainerContainsEmptyKey_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .outputContainer(new HashMap<String, Double>() {
                    {
                        put("", .1);
                        put("key2", .1);
                    }
                })
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);

        List<String> expected = Collections.singletonList(ValidationMessage.State.OC_VALUES + "[<blank> : 0.1]");

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("outputContainer contains null key")
    void validate_outputContainerContainsNullKey_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .outputContainer(new HashMap<String, Double>() {
                    {
                        put(null, .1);
                        put("key2", .1);
                    }
                })
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);

        List<String> expected = Collections.singletonList(ValidationMessage.State.OC_VALUES + "[<null> : 0.1]");

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("outputContainer contains null value")
    void validate_outputContainerContainsNullValue_expectedMessage() {
        State state = State.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(VALID_INT)
                .positionY(VALID_INT)
                .outputContainer(new HashMap<String, Double>() {
                    {
                        put("key1", null);
                        put("key2", .1);
                    }
                })
                .style(Style.builder()
                        .sourceAnchorStyle(VALID_STR)
                        .sourceStyle(VALID_STR)
                        .targetAnchorStyle(VALID_STR)
                        .targetStyle(VALID_STR)
                        .build())
                .build();


        Set<ConstraintViolation<State>> constraintViolations = validator.validate(state);

        List<String> expected = Collections.singletonList(ValidationMessage.State.OC_VALUES + "[key1 : <null>]");

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

}