package com.hubo.gillajabi.global.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<EnumValid, String> {

    private String[] acceptedValues;

    @Override
    public void initialize(EnumValid annotation) {
        this.acceptedValues = annotation.acceptedValues();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // null값은 다른 @NotNull 등으로 검증
        if (value == null) {
            return true;
        }
        return Arrays.asList(acceptedValues).contains(value);
    }
}