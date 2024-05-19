package org.tolking.animeharbor.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.tolking.animeharbor.repositories.StudioRepository;
import org.tolking.animeharbor.validator.annotation.UniqueStudioTitle;

@Component
@RequiredArgsConstructor
public class UniqueStudioTitleValidator implements ConstraintValidator<UniqueStudioTitle, String> {
    private final StudioRepository studioRepository;

    @Override
    public void initialize(UniqueStudioTitle constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !studioRepository.existsByNameEqualsIgnoreCase(value);
    }
}
