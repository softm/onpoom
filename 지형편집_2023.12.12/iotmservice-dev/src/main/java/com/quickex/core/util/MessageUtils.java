package com.quickex.core.util;

import com.quickex.core.util.spring.SpringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Get I18N resource file
 *
 * @author ffzh.thero
 */
public class MessageUtils {
    /**
     * Get the message according to the message key and parameters and delegate it to the spring MessageSource
     *
     * @param code Message key
     * @param args parameter
     * @return Get international translation value
     */
    public static String message(String code, Object... args) {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
