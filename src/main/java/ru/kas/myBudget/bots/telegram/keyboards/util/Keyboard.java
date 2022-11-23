package ru.kas.myBudget.bots.telegram.keyboards.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface Keyboard {
    InlineKeyboardMarkup getKeyboard();
}
