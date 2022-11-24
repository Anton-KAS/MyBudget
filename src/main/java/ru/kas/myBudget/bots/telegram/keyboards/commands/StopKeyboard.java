package ru.kas.myBudget.bots.telegram.keyboards.commands;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.util.Keyboard;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

public class StopKeyboard implements Keyboard {
    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return null;
    }
}
