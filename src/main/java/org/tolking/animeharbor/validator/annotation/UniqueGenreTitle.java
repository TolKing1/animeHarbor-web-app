package org.tolking.animeharbor.validator.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.tolking.animeharbor.validator.UniqueGenreTitleValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueGenreTitleValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueGenreTitle {
    String message() default "Genre already exists.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
