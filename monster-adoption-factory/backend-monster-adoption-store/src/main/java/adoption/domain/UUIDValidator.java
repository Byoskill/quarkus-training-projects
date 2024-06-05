package adoption.domain;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UUIDValidator
        implements ConstraintValidator<UUIDValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        if (value == null)
            return true;
        try {
            return java.util.UUID.fromString(value) != null && value.length() > 15;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
