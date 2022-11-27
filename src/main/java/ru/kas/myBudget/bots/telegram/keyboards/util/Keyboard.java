package ru.kas.myBudget.bots.telegram.keyboards.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public interface Keyboard {
    InlineKeyboardMarkup getKeyboard();
}
