package io.github.ismayilibrahimov.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NotEmptyImageValidator.class})
public @interface NotEmptyImage {
    String message() default "Please select a image file";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
