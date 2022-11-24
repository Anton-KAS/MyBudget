package ru.kas.myBudget.bots.telegram.keyboards.AccountDialog;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.DialogKeyboardImpl;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

public abstract class ConfirmKeyboard extends DialogKeyboardImpl {

    public ConfirmKeyboard(String currentDialogName) {
        super(currentDialogName);
    }

    @Override
    abstract public InlineKeyboardMarkup getKeyboard();
}
