package ru.kas.myBudget.bots.telegram.keyboards.addAccountDialog;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.DialogKeyboardImpl;

public class SaveKeyboard extends DialogKeyboardImpl {
    public SaveKeyboard(String currentDialogName) {
        super(currentDialogName);
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return null;
    }
}
