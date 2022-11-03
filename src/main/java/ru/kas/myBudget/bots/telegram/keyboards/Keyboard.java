package ru.kas.myBudget.bots.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface Keyboard {
    InlineKeyboardMarkup getKeyboard();

    default InlineKeyboardButton getButton(String text, String callbackData) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callbackData);
        return inlineKeyboardButton;
    }

    default InlineKeyboardButton getReturnButton(String from, String to) {
        return getButton("<- Назад", from + "_" + to);
    }

    default InlineKeyboardButton getCloseButton(String from) {
        return getButton("X Закрыть", from + "_close");
    }
}
