package com.hubo.gillajabi.global.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
* EnumValid.java
* ENUM 타입을 검증하기 위해 사용하는 어노테이션
* 특정
 */
@Constraint(validatedBy = EnumValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValid {
    // 허용된 녀석들
    String[] acceptedValues();

    // jakarta.validation.constraints 참고
    String message() default "This value is not allowed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}