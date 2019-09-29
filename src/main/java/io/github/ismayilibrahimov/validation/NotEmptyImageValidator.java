package io.github.ismayilibrahimov.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class NotEmptyImageValidator implements ConstraintValidator<NotEmptyImage, MultipartFile> {
    @Override
    public void initialize(NotEmptyImage constraintAnnotation) {

    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {

        boolean result = true;

        if (multipartFile.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Please select a image file.")
                    .addConstraintViolation();
            result = false;
        }

        return result;
    }

}