package org.tolking.animeharbor.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.tolking.animeharbor.repositories.GenreRepository;
import org.tolking.animeharbor.validator.annotation.UniqueGenreTitle;

@Component
@RequiredArgsConstructor
public class UniqueGenreTitleValidator implements ConstraintValidator<UniqueGenreTitle, String> {
    private final GenreRepository genreRepository;

    @Override
    public void initialize(UniqueGenreTitle unique) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !genreRepository.existsByTitleEqualsIgnoreCase(value);
    }
}
