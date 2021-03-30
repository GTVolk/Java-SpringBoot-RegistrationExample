package ru.devvault.skilltest.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Реализация аннотации сравнения полей для валидатора
 *
 * Для описания см. интерфейс
 */
@Slf4j
public class FieldMatchImpl implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @SneakyThrows
    @Override
    public boolean isValid(final Object value,
        final ConstraintValidatorContext context) {
        boolean valid;

        Object firstObj = null;
        Object secondObj = null;
        try {
            firstObj = BeanUtils.getProperty(value, firstFieldName);
            secondObj = BeanUtils.getProperty(value, secondFieldName);
            valid = (firstObj == null && secondObj == null)
                || (firstObj != null && firstObj.equals(secondObj));
        } catch (final Exception e) {
            log.error("firstObj: " + (firstObj != null ? firstObj.toString() : ""));
            log.error("secondObj: " + (secondObj != null ? secondObj.toString() : ""));
            throw e;
        }
        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(secondFieldName)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(firstFieldName)
                .addConstraintViolation();
        }
        return valid;
    }
}
