package com.hubo.gillajabi.global.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<EnumValid, Enum<?>> {

    private String[] acceptedValues;

    @Override
    public void initialize(EnumValid annotation) {
        this.acceptedValues = annotation.acceptedValues();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return Arrays.asList(acceptedValues).contains(value.name());
    }
}
