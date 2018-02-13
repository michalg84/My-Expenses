package pl.sda.validators;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { CurrencyValidator.class })
public @interface Currency {
    String message() default "Invalid entry. Please insert valid amount";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
