package com.quickex.config.log;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperLog {
    OperType operType();
    String operDesc() default "";
}
