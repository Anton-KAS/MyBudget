package ru.kas.myBudget.bots.telegram.dialogs;

import java.util.regex.Pattern;

public enum DialogPattern {
    EDIT_NUM("/\\d "),
    CURRENCY_AMOUNT("\\d+[\\.\\,]?\\d+");

    private final String regex;

    DialogPattern(String pattern) {
        this.regex = pattern;
    }

    public String getRegex() {
        return regex;
    }

    public Pattern getPattern() {
        return Pattern.compile(regex);
    }
}
