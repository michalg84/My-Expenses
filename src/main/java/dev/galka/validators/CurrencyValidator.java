package dev.galka.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class CurrencyValidator implements ConstraintValidator<Currency, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return value instanceof BigDecimal;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
