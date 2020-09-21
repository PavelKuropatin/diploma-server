package by.bntu.constructor.domain;

import by.bntu.constructor.domain.constraint.util.ValidationMessage;
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

class SchemaTest {

    private static final String VALID_STR = RandomStringUtils.random(127, true, true);

    private static Validator validator;
    private static Block block;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        block = Block.builder()
                .name(VALID_STR)
                .color(VALID_STR)
                .template(VALID_STR)
                .positionX(RandomUtils.nextDouble())
                .positionY(RandomUtils.nextDouble())
                .style(Style.builder()
                        .inputAnchorStyle(VALID_STR)
                        .inputStyle(VALID_STR)
                        .outputAnchorStyle(VALID_STR)
                        .outputStyle(VALID_STR)
                        .build())
                .build();
    }

    @Test
    @DisplayName("valid blocks")
    void validate_validBlocks_expectedNoMessage() {
        Schema schema = Schema.builder()
                .name(VALID_STR)
                .description(VALID_STR)
                .blocks(Collections.singletonList(block))
                .build();

        Set<ConstraintViolation<Schema>> constraintViolations = validator.validate(schema);

        assertTrue(constraintViolations.isEmpty());
    }


    @Test
    @DisplayName("blocks null")
    void validate_blocksNull_expectedMessage() {
        Schema schema = Schema.builder()
                .name(VALID_STR)
                .description(VALID_STR)
                .blocks(null)
                .build();

        Set<ConstraintViolation<Schema>> constraintViolations = validator.validate(schema);
        List<String> expected = Collections.singletonList(ValidationMessage.Schema.BLOCKS_NULL);

        List<String> actual = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "run #[{index}] {2}")
    @DisplayName("parametrized")
    @CsvFileSource(resources = "/schema.csv", numLinesToSkip = 1)
    void validate__expectedMessage(String name, String description, String messages) {

        Schema schema = Schema.builder()
                .name(name)
                .description(description)
                .build();


        List<String> actual = validator.validate(schema).stream()
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