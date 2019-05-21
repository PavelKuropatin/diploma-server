package by.bntu.diploma.diagram.domain.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NullOrMinValidator implements ConstraintValidator<NullOrMin, Integer> {

    private int min;

    @Override
    public void initialize(NullOrMin annotation) {
        min = annotation.min();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value >= min;
    }


}
