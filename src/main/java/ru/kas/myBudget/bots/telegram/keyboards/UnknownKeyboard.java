package ru.kas.myBudget.bots.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class UnknownKeyboard implements Keyboard{
    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return null;
    }
}
