package com.byoskill.domain.adoption.model;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target({ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,
        ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UUIDValidator.class)
public @interface UUIDValid {

    String message() default "UUID is not valid";

    Class<?>[] groups() default {};

    Class<String>[] payload() default {};

}