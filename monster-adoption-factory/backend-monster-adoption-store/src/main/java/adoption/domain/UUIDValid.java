package adoption.domain;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;


@Target({ ElementType.METHOD,
        ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { UUIDValidator.class })
public @interface UUIDValid {

    String message() default "UUID is not valid";

    Class<?>[] groups() default {};

    Class<? extends String>[] payload() default {};

}
