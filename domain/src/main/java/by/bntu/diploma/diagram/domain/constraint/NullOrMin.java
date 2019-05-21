package by.bntu.diploma.diagram.domain.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Repeatable(NullOrMin.List.class)
@Documented
@Constraint(validatedBy = NullOrMinValidator.class)
public @interface NullOrMin {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 0;

    @Target({FIELD, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        NullOrMin[] value();
    }
}
