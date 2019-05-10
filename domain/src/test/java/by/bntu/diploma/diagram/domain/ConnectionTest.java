package by.bntu.diploma.diagram.domain;

import by.bntu.diploma.diagram.domain.constraint.util.ValidationMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

class ConnectionTest {

    private static Validator validator;

    private static Stream<Arguments> provideObj() {
        return Stream.of(
                Arguments.of(null, null, new String[]{ValidationMessage.Connection.SOURCE_NULL, ValidationMessage.Connection.TARGET_NULL}),
                Arguments.of(Source.builder().build(), null, new String[]{ValidationMessage.Connection.TARGET_NULL}),
                Arguments.of(null, Target.builder().build(), new String[]{ValidationMessage.Connection.SOURCE_NULL}),
                Arguments.of(Source.builder().build(), Target.builder().build(), new String[0])
        );
    }

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @DisplayName("any source & targets")
    @MethodSource("provideObj")
    void validate___expectedMessage(Source source, Target target, String[] messages) {
        Connection connection = Connection.builder()
                .source(source)
                .target(target)
                .build();

        Set<ConstraintViolation<Connection>> constraintViolations = validator.validate(connection);
        List<String> expected = Arrays.asList(messages);

        String[] actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .toArray(String[]::new);

        assertThat(expected, hasItems(actual));
    }

}