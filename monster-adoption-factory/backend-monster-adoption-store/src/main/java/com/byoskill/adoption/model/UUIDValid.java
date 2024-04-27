package com.byoskill.adoption.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Target({ ElementType.METHOD, 
        ElementType.FIELD, 
        ElementType.ANNOTATION_TYPE, 
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, 
        ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { UUIDValidator.class })
public @interface UUIDValid {

    String message() default "UUID is not valid";

    Class<?>[] groups() default {};

    Class<? extends String>[] payload() default {};

}