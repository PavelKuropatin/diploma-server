package by.bntu.diagram.domain;

import org.apache.commons.lang3.RandomStringUtils;
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

class StateTest {

    private static final String VALID_STR = RandomStringUtils.random(127, true, true);

    private static Validator validator;
    private static Style style;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        style = Style.builder()
                .sourceAnchorStyle(VALID_STR)
                .sourceStyle(VALID_STR)
                .targetAnchorStyle(VALID_STR)
                .targetStyle(VALID_STR)
                .build();

    }

    @ParameterizedTest(name = "run #[{index}] {5}")
    @DisplayName("parametrized")
    @CsvFileSource(resources = "/state.csv", numLinesToSkip = 1)
    void validate__expectedMessage(String name, String color, String template, Double positionX, Double positionY, String messages) {

        State state = State.builder()
                .name(name)
                .color(color)
                .template(template)
                .positionX(positionX)
                .positionY(positionY)
                .style(style)
                .build();


        List<String> actual = validator.validate(state).stream()
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