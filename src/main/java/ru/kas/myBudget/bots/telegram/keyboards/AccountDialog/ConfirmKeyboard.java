package ru.kas.myBudget.bots.telegram.keyboards.AccountDialog;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.DialogKeyboardImpl;

public abstract class ConfirmKeyboard extends DialogKeyboardImpl {

    public ConfirmKeyboard(String currentDialogName) {
        super(currentDialogName);
    }

    @Override
    abstract public InlineKeyboardMarkup getKeyboard();
}
