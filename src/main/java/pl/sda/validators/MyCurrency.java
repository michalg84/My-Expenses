package pl.sda.validators;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { MyCurrencyValidator.class })
public @interface MyCurrency {
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
