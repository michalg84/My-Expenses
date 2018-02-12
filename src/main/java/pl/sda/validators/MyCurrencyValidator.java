package pl.sda.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class MyCurrencyValidator implements ConstraintValidator<MyCurrency, String> {

    @Override
    public void initialize(MyCurrency constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
