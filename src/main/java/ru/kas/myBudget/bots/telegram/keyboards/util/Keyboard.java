package ru.kas.myBudget.bots.telegram.keyboards.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

public interface Keyboard {
    InlineKeyboardMarkup getKeyboard();
}
