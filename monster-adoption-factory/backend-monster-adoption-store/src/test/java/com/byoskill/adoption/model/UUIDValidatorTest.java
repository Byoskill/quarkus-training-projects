package com.byoskill.adoption.model;

import com.byoskill.domain.adoption.model.UUIDValid;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class UUIDValidatorTest {

    @Test
    public void testUUIDValidation() {
        Assertions.assertEquals(0, this.validate("123e4567-e89b-12d3-a456-426655440000").size());
        Assertions.assertEquals(1, this.validate("").size());
    }

    private Set<ConstraintViolation<Model>> validate(final String uuidValue) {
        final ValidatorFactory validatorFactory = Validation.byDefaultProvider()
                .configure()
                .buildValidatorFactory();
        final Validator validator = validatorFactory.getValidator();
        final Set<ConstraintViolation<Model>> violations = validator.validate(new Model(uuidValue));
        return violations;
    }

    public class Model {

        @UUIDValid
        public String uuid;

        public Model(final String uuid) {
            this.uuid = uuid;
        }
    }

}
