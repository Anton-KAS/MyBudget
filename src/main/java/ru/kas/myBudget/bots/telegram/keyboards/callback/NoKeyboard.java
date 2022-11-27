package ru.kas.myBudget.bots.telegram.keyboards.callback;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.util.Keyboard;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class NoKeyboard implements Keyboard {
    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return null;
    }
}
