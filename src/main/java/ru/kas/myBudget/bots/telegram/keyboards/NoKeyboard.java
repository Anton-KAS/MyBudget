package ru.kas.myBudget.bots.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class NoKeyboard  implements Keyboard{
    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return null;
    }
}
