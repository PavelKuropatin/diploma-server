package by.bntu.diploma.diagram.domain;

import by.bntu.diploma.diagram.domain.constraint.util.ValidationMessage;
import org.apache.commons.lang3.RandomStringUtils;
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

class StyleTest {

    private static final String VALID_STR = RandomStringUtils.random(127, true, true);
    private static final String INVALID_STR_256 = RandomStringUtils.random(256, true, true);
    private static final String INVALID_STR_2 = RandomStringUtils.random(2, true, true);

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("sourceAnchorStyle null")
    void validate_sourceAnchorStyleNull_expectedMessage() {
        Style style = Style.builder()
                .sourceAnchorStyle(null)
                .sourceStyle(VALID_STR)
                .targetAnchorStyle(VALID_STR)
                .targetStyle(VALID_STR)
                .build();

        Set<ConstraintViolation<Style>> constraintViolations = validator.validate(style);
        List<String> expected = Collections.singletonList(ValidationMessage.Style.SOURCE_ANCHOR_STYLE_BLANK);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("sourceAnchorStyle blank")
    void validate_sourceAnchorStyleBlank_expectedTwoMessage() {
        Style style = Style.builder()
                .sourceAnchorStyle(StringUtils.SPACE)
                .sourceStyle(VALID_STR)
                .targetAnchorStyle(VALID_STR)
                .targetStyle(VALID_STR)
                .build();
        Set<ConstraintViolation<Style>> constraintViolations = validator.validate(style);

        List<String> expected = Stream.of(ValidationMessage.Style.SOURCE_ANCHOR_STYLE_BLANK, ValidationMessage.Style.SOURCE_ANCHOR_STYLE_SIZE)
                .sorted()
                .collect(Collectors.toList());
        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("sourceAnchorStyle less min size")
    void validate_sourceAnchorStyleLessMinSize_expectedTwoMessage() {
        Style style = Style.builder()
                .sourceAnchorStyle(INVALID_STR_2)
                .sourceStyle(VALID_STR)
                .targetAnchorStyle(VALID_STR)
                .targetStyle(VALID_STR)
                .build();
        Set<ConstraintViolation<Style>> constraintViolations = validator.validate(style);

        List<String> expected = Collections.singletonList(ValidationMessage.Style.SOURCE_ANCHOR_STYLE_SIZE);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("sourceAnchorStyle more max size")
    void validate_sourceAnchorStyleMoreMaxSize_expectedTwoMessage() {
        Style style = Style.builder()
                .sourceAnchorStyle(INVALID_STR_256)
                .sourceStyle(VALID_STR)
                .targetAnchorStyle(VALID_STR)
                .targetStyle(VALID_STR)
                .build();
        Set<ConstraintViolation<Style>> constraintViolations = validator.validate(style);

        List<String> expected = Collections.singletonList(ValidationMessage.Style.SOURCE_ANCHOR_STYLE_SIZE);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("sourceStyle null")
    void validate_sourceStyleNull_expectedMessage() {
        Style style = Style.builder()
                .sourceAnchorStyle(VALID_STR)
                .sourceStyle(null)
                .targetAnchorStyle(VALID_STR)
                .targetStyle(VALID_STR)
                .build();

        Set<ConstraintViolation<Style>> constraintViolations = validator.validate(style);
        List<String> expected = Collections.singletonList(ValidationMessage.Style.SOURCE_STYLE_BLANK);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("sourceStyle blank")
    void validate_sourceStyleBlank_expectedTwoMessage() {
        Style style = Style.builder()
                .sourceAnchorStyle(VALID_STR)
                .sourceStyle(StringUtils.SPACE)
                .targetAnchorStyle(VALID_STR)
                .targetStyle(VALID_STR)
                .build();
        Set<ConstraintViolation<Style>> constraintViolations = validator.validate(style);

        List<String> expected = Stream.of(ValidationMessage.Style.SOURCE_STYLE_BLANK, ValidationMessage.Style.SOURCE_STYLE_SIZE)
                .sorted()
                .collect(Collectors.toList());
        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("sourceStyle less min size")
    void validate_sourceStyleLessMinSize_expectedTwoMessage() {
        Style style = Style.builder()
                .sourceAnchorStyle(VALID_STR)
                .sourceStyle(INVALID_STR_2)
                .targetAnchorStyle(VALID_STR)
                .targetStyle(VALID_STR)
                .build();
        Set<ConstraintViolation<Style>> constraintViolations = validator.validate(style);

        List<String> expected = Collections.singletonList(ValidationMessage.Style.SOURCE_STYLE_SIZE);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("sourceStyle more max size")
    void validate_sourceStyleMoreMaxSize_expectedTwoMessage() {
        Style style = Style.builder()
                .sourceAnchorStyle(VALID_STR)
                .sourceStyle(INVALID_STR_256)
                .targetAnchorStyle(VALID_STR)
                .targetStyle(VALID_STR)
                .build();
        Set<ConstraintViolation<Style>> constraintViolations = validator.validate(style);

        List<String> expected = Collections.singletonList(ValidationMessage.Style.SOURCE_STYLE_SIZE);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("targetAnchorStyle null")
    void validate_targetAnchorStyleNull_expectedMessage() {
        Style style = Style.builder()
                .sourceAnchorStyle(VALID_STR)
                .sourceStyle(VALID_STR)
                .targetAnchorStyle(null)
                .targetStyle(VALID_STR)
                .build();

        Set<ConstraintViolation<Style>> constraintViolations = validator.validate(style);
        List<String> expected = Collections.singletonList(ValidationMessage.Style.TARGET_ANCHOR_STYLE_BLANK);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("targetAnchorStyle blank")
    void validate_targetAnchorStyleBlank_expectedTwoMessage() {
        Style style = Style.builder()
                .sourceAnchorStyle(VALID_STR)
                .sourceStyle(VALID_STR)
                .targetAnchorStyle(StringUtils.SPACE)
                .targetStyle(VALID_STR)
                .build();
        Set<ConstraintViolation<Style>> constraintViolations = validator.validate(style);

        List<String> expected = Stream.of(ValidationMessage.Style.TARGET_ANCHOR_STYLE_BLANK, ValidationMessage.Style.TARGET_ANCHOR_STYLE_SIZE)
                .sorted()
                .collect(Collectors.toList());
        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("targetAnchorStyle less min size")
    void validate_targetAnchorStyleLessMinSize_expectedTwoMessage() {
        Style style = Style.builder()
                .sourceAnchorStyle(VALID_STR)
                .sourceStyle(VALID_STR)
                .targetAnchorStyle(INVALID_STR_2)
                .targetStyle(VALID_STR)
                .build();
        Set<ConstraintViolation<Style>> constraintViolations = validator.validate(style);

        List<String> expected = Collections.singletonList(ValidationMessage.Style.TARGET_ANCHOR_STYLE_SIZE);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("targetAnchorStyle more max size")
    void validate_targetAnchorStyleMoreValidSize_expectedTwoMessage() {
        Style style = Style.builder()
                .sourceAnchorStyle(VALID_STR)
                .sourceStyle(VALID_STR)
                .targetAnchorStyle(INVALID_STR_256)
                .targetStyle(VALID_STR)
                .build();
        Set<ConstraintViolation<Style>> constraintViolations = validator.validate(style);

        List<String> expected = Collections.singletonList(ValidationMessage.Style.TARGET_ANCHOR_STYLE_SIZE);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("targetStyle null")
    void validate_targetStyleNull_expectedMessage() {
        Style style = Style.builder()
                .sourceAnchorStyle(VALID_STR)
                .sourceStyle(VALID_STR)
                .targetAnchorStyle(VALID_STR)
                .targetStyle(null)
                .build();

        Set<ConstraintViolation<Style>> constraintViolations = validator.validate(style);
        List<String> expected = Collections.singletonList(ValidationMessage.Style.TARGET_STYLE_BLANK);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("targetStyle blank")
    void validate_targetStyleBlank_expectedTwoMessage() {
        Style style = Style.builder()
                .sourceAnchorStyle(VALID_STR)
                .sourceStyle(VALID_STR)
                .targetAnchorStyle(VALID_STR)
                .targetStyle(StringUtils.SPACE)
                .build();
        Set<ConstraintViolation<Style>> constraintViolations = validator.validate(style);

        List<String> expected = Stream.of(ValidationMessage.Style.TARGET_STYLE_BLANK, ValidationMessage.Style.TARGET_STYLE_SIZE)
                .sorted()
                .collect(Collectors.toList());
        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("targetStyle less min size")
    void validate_targetStyleLessMinSize_expectedTwoMessage() {
        Style style = Style.builder()
                .sourceAnchorStyle(VALID_STR)
                .sourceStyle(VALID_STR)
                .targetAnchorStyle(VALID_STR)
                .targetStyle(INVALID_STR_256)
                .build();
        Set<ConstraintViolation<Style>> constraintViolations = validator.validate(style);

        List<String> expected = Collections.singletonList(ValidationMessage.Style.TARGET_STYLE_SIZE);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("targetStyle more max size")
    void validate_targetStyleMoreMaxSize_expectedTwoMessage() {
        Style style = Style.builder()
                .sourceAnchorStyle(VALID_STR)
                .sourceStyle(VALID_STR)
                .targetAnchorStyle(VALID_STR)
                .targetStyle(INVALID_STR_256)
                .build();
        Set<ConstraintViolation<Style>> constraintViolations = validator.validate(style);

        List<String> expected = Collections.singletonList(ValidationMessage.Style.TARGET_STYLE_SIZE);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }
}