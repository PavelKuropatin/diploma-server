package by.bntu.constructor.domain.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NullOrSizeValidator implements ConstraintValidator<NullOrSize, String> {

    private int min;
    private int max;

    @Override
    public void initialize(NullOrSize annotation) {
        min = annotation.min();
        max = annotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.length() >= min && value.length() <= max;
    }


}
