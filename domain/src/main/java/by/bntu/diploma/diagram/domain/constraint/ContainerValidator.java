package by.bntu.diploma.diagram.domain.constraint;

import by.bntu.diploma.diagram.domain.ContainerType;
import by.bntu.diploma.diagram.domain.constraint.util.ValidationMessage;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ContainerValidator implements ConstraintValidator<ValidContainer, Map<String, Double>> {

    private static final String NULL = "<null>";
    private static final String BLANK = "<blank>";
    private ContainerType type;
    private String nullMessage;
    private String valuesMessage;

    private String buildMessage(String key, Double value) {
        if (Objects.isNull(key)) {
            key = NULL;
        }
        if (StringUtils.isBlank(key)) {
            key = BLANK;
        }
        String sValue;
        if (Objects.isNull(value)) {
            sValue = NULL;
        } else {
            sValue = value.toString();
        }
        return key + " : " + sValue;
    }

    @Override
    public void initialize(ValidContainer constraintAnnotation) {
        this.type = constraintAnnotation.type();
        if (type == ContainerType.INPUT) {
            this.nullMessage = ValidationMessage.State.IC_NULL;
            this.valuesMessage = ValidationMessage.State.IC_VALUES;
        } else {
            this.nullMessage = ValidationMessage.State.OC_NULL;
            this.valuesMessage = ValidationMessage.State.OC_VALUES;
        }
    }

    @Override
    public boolean isValid(Map<String, Double> container, ConstraintValidatorContext context) {
        if (container == null) {
            applyMessage(context, this.nullMessage);
            return false;
        }
        if (container.isEmpty()) {
            return true;
        }
        List<String> invalidKeys = container.keySet().stream()
                .filter(key -> !(StringUtils.isNotBlank(key) && !Objects.isNull(container.get(key))))
                .collect(Collectors.toList());
        if (invalidKeys.isEmpty()) {
            return true;
        }
        String message = context.getDefaultConstraintMessageTemplate();

        message = this.valuesMessage + invalidKeys.stream()
                .map(key -> buildMessage(key, container.get(key)))
                .collect(Collectors.joining(";", "[", "]"));

        applyMessage(context, message);
        return false;
    }

    private void applyMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }


}
