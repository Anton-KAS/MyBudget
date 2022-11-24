package ru.kas.myBudget.bots.telegram.keyboards.AccountDialog;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.DialogKeyboardImpl;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

public class SaveKeyboard extends DialogKeyboardImpl {
    public SaveKeyboard(String currentDialogName) {
        super(currentDialogName);
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return null;
    }
}
