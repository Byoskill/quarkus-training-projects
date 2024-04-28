package com.byoskill.domain.adoption.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UUIDValidator
        implements ConstraintValidator<UUIDValid, String> {

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext ctx) {
        if (null == value)
            return true;
        try {
            return null != java.util.UUID.fromString(value) && 15 < value.length();
        } catch (final IllegalArgumentException e) {
            return false;
        }
    }
}