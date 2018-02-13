package pl.sda.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class CurrencyValidator implements ConstraintValidator<Currency, Object> {

    @Override
    public void initialize(Currency constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (value != null || value instanceof BigDecimal)
                return true;
            else
                return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
