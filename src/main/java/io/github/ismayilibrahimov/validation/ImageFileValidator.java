package io.github.ismayilibrahimov.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageFileValidator implements ConstraintValidator<ValidImage, MultipartFile> {
    @Override
    public void initialize(ValidImage constraintAnnotation) {

    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {

        boolean result = true;

        String contentType = multipartFile.getContentType();
        if (!multipartFile.isEmpty()) {
            if (!isSupportedContentType(contentType)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        "Only PNG or JPG images are allowed.")
                        .addConstraintViolation();

                result = false;
            }
        }

        return result;
    }

    private boolean isSupportedContentType(String contentType) {
        return "image/png".equals(contentType)
                || "image/jpg".equals(contentType)
                || "image/jpeg".equals(contentType);
    }

}
