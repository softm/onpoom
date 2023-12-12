package com.quickex.core.text;


import com.quickex.core.util.StringUtils;

/**
 * String formatting
 *
 * @author .thero
 */
public class StrFormatter {
    public static final String EMPTY_JSON = "{}";
    public static final char C_BACKSLASH = '\\';
    public static final char C_DELIM_START = '{';
    public static final char C_DELIM_END = '}';

    /**

     *Format string < br >

     *This method simply replaces the placeholder {} with the parameter < br > in order

     *If you want to output {}, you can use \ \ escape {. If you want to output \ before {}, you can use double escape character \ \ \ < br >

     *Example: < br >

     *Usually: format ("this is {} for {}", "a", "B") - > this is a for B < br >

     *Escape {}: format ("this is \ {} for {}", "a", "B") - > this is \ {} for a < br >

     *Escape \: format ("this is \ \ \ \ {} for {}", "a", "B") - > this is \ a for B < br >

     *

     *@ param strpattern string template

     *@ param argarray parameter list

     *@ return result

     */
    public static String format(final String strPattern, final Object... argArray) {
        if (StringUtils.isEmpty(strPattern) || StringUtils.isEmpty(argArray)) {
            return strPattern;
        }
        final int strPatternLength = strPattern.length();

        // Initialize the defined length for better performance
        StringBuilder sbuf = new StringBuilder(strPatternLength + 50);

        int handledPosition = 0;
        int delimIndex;// Placeholder location
        for (int argIndex = 0; argIndex < argArray.length; argIndex++) {
            delimIndex = strPattern.indexOf(EMPTY_JSON, handledPosition);
            if (delimIndex == -1) {
                if (handledPosition == 0) {
                    return strPattern;
                } else { // The rest of the string template no longer contains placeholders. The results will be returned after adding the rest
                    sbuf.append(strPattern, handledPosition, strPatternLength);
                    return sbuf.toString();
                }
            } else {
                if (delimIndex > 0 && strPattern.charAt(delimIndex - 1) == C_BACKSLASH) {
                    if (delimIndex > 1 && strPattern.charAt(delimIndex - 2) == C_BACKSLASH) {
                        // There is an escape character before the escape character, and the placeholder is still valid
                        sbuf.append(strPattern, handledPosition, delimIndex - 1);
                        sbuf.append(Convert.utf8Str(argArray[argIndex]));
                        handledPosition = delimIndex + 2;
                    } else {
                        // Placeholder escaped
                        argIndex--;
                        sbuf.append(strPattern, handledPosition, delimIndex - 1);
                        sbuf.append(C_DELIM_START);
                        handledPosition = delimIndex + 1;
                    }
                } else {
                    // Normal placeholder
                    sbuf.append(strPattern, handledPosition, delimIndex);
                    sbuf.append(Convert.utf8Str(argArray[argIndex]));
                    handledPosition = delimIndex + 2;
                }
            }
        }
        // All characters after adding the last placeholder
        sbuf.append(strPattern, handledPosition, strPattern.length());

        return sbuf.toString();
    }
}
