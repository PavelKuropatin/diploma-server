package by.bntu.constructor.domain;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StyleTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest(name = "run #[{index}] {4}")
    @DisplayName("parametrized")
    @CsvFileSource(resources = "/style.csv", numLinesToSkip = 1)
    void validate__expectedMessage(String inputAnchorStyle, String inputStyle,
                                   String outputAnchorStyle, String outputStyle, String messages) {
        Style style = Style.builder()
                .inputAnchorStyle(inputAnchorStyle)
                .inputStyle(inputStyle)
                .outputAnchorStyle(outputAnchorStyle)
                .outputStyle(outputStyle)
                .build();

        List<String> actual = validator.validate(style).stream()
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