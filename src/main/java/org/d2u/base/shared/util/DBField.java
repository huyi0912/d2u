package org.d2u.base.shared.util;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DBField {
    boolean primaryKey() default false;
    boolean notNull() default false;
    String foreignTableColumn() default "";

}
