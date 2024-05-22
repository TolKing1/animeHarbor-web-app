package org.tolking.animeharbor.validator.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.tolking.animeharbor.validator.UniqueStudioTitleValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueStudioTitleValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueStudioTitle {
    String message() default "Studio already exists.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
