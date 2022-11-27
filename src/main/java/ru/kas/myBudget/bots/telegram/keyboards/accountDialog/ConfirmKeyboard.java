package ru.kas.myBudget.bots.telegram.keyboards.accountDialog;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.DialogKeyboardImpl;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public abstract class ConfirmKeyboard extends DialogKeyboardImpl {

    public ConfirmKeyboard(String currentDialogName) {
        super(currentDialogName);
    }

    @Override
    abstract public InlineKeyboardMarkup getKeyboard();
}
