package ru.kas.myBudget.bots.telegram.dialogs.util;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public enum DialogPattern {
    EDIT_NUM("/\\d+"),
    CURRENCY_AMOUNT("\\d+[\\.\\,]?\\d*");

    private final String regex;

    DialogPattern(String pattern) {
        this.regex = pattern;
    }

    public String getRegex() {
        return regex;
    }
}
