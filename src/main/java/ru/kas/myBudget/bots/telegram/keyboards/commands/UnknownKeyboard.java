package ru.kas.myBudget.bots.telegram.keyboards.commands;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;

public class UnknownKeyboard implements Keyboard {
    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return null;
    }
}
