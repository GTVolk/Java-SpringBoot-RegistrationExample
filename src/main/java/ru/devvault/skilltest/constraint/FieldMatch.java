package ru.devvault.skilltest.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Аннотация для поддержки одинаковых значений полей, используется для валидатора
 *
 * При указании над бином можно указать список полей которые будут сравниваться на равенство, и если не равны будет выдано сообщение как ошибка
 */
@Target({
    ElementType.TYPE,
    ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldMatchImpl.class)
@Documented
public @interface FieldMatch {

    String message() default "{constraints.field-match}";
    Class < ? > [] groups() default {};
    Class < ? extends Payload > [] payload() default {};
    String first();
    String second();
    @Target({
        ElementType.TYPE,
        ElementType.ANNOTATION_TYPE
    })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        FieldMatch[] value();
    }
}
