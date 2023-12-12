package com.quickex.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;

/**
 * Custom export Excel data annotation
 * 
 * @author ffzh
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel
{
    /**
     * Sort in excel when exporting
     */
    int sort() default Integer.MAX_VALUE;

    /**
     * Name exported to excel
     */
    String name() default "";

    /**
     * Date format, such as yyyy MM DD
     */
    String dateFormat() default "";

    /**
     * If it is a dictionary type, set the type value of the dictionary (for example: sys_user_sex)
     */
    String dictType() default "";

    /**
     * Read content to expression (e.g. 0 = male, 1 = female, 2 = unknown)
     */
    String readConverterExp() default "";

    /**
     * Separator to read the contents of the string group
     */
    String separator() default ",";

    /**
     * BigDecimal precision default: - 1 (BigDecimal formatting is not enabled by default)
     */
    int scale() default -1;

    /**
     * BigDecimal rounding rule default: BigDecimal ROUND_ HALF_ EVEN
     */
    int roundingMode() default BigDecimal.ROUND_HALF_EVEN;

    /**
     * Export type (0 digit 1 string)
     */
    ColumnType cellType() default ColumnType.STRING;

    /**
     * When exporting, the height unit of each column in excel is character
     */
    double height() default 14;

    /**
     * When exporting, the width unit of each column in excel is characters
     */
    double width() default 16;

    /**
     * Text suffix, such as% 90 becomes 90%
     */
    String suffix() default "";

    /**
     * When the value is empty, the default value of the field
     */
    String defaultValue() default "";

    /**
     * Prompt information
     */
    String prompt() default "";

    /**
     * Only columns that cannot be entered can be selected
     */
    String[] combo() default {};

    /**
     * Whether to export data and respond to requirements: sometimes we need to export a template, which is the title, but the content needs to be filled in manually
     */
    boolean isExport() default true;

    /**
     * Attribute names in another class support multi-level acquisition, separated by decimal points
     */
    String targetAttr() default "";

    /**
     * Whether to automatically add statistics, and add the sum of statistics in the last row
     */
    boolean isStatistics() default false;

    /**
     * Export field alignment (0: default; 1: left; 2: Center; 3: right)
     */
    Align align() default Align.AUTO;

    enum Align
    {
        AUTO(0), LEFT(1), CENTER(2), RIGHT(3);
        private final int value;

        Align(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }

    /**
     * Field type (0: export import; 1: export only; 2: import only)
     */
    Type type() default Type.ALL;

    enum Type
    {
        ALL(0), EXPORT(1), IMPORT(2);
        private final int value;

        Type(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }

    enum ColumnType
    {
        NUMERIC(0), STRING(1), IMAGE(2);
        private final int value;

        ColumnType(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }
}