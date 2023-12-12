package com.quickex.core.annotation;



import com.quickex.core.enums.BusinessType;
import com.quickex.core.enums.OperatorType;

import java.lang.annotation.*;

/**
 * Custom action logging annotation
 * 
 * @author ffzh
 *
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log
{
    /**
     * modular
     */
    String title() default "";

    /**
     * function
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * Operator category
     */
    OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * Save requested parameters
     */
    boolean isSaveRequestData() default true;
}
