package com.byoskill.adoption.model;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UUIDValidatorTest {

    public class Model {

        public Model(String uuid) {
            this.uuid = uuid;
        }

        @UUIDValid
        public String uuid;
    }

    @Test
    public void testUUIDValidation() {
        Assertions.assertEquals(0, validate("123e4567-e89b-12d3-a456-426655440000").size());
        Assertions.assertEquals(1, validate("").size());
    }

    private Set<ConstraintViolation<Model>> validate(String uuidValue) {
        ValidatorFactory validatorFactory = Validation.byDefaultProvider()
                .configure()
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Model>> violations = validator.validate(new Model(uuidValue));
        return violations;
    }
    
}
