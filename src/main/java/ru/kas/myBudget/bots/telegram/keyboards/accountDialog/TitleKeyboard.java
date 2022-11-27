package ru.kas.myBudget.bots.telegram.keyboards.accountDialog;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.DialogKeyboardImpl;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class TitleKeyboard extends DialogKeyboardImpl {
    public TitleKeyboard(String currentDialogName) {
        super(currentDialogName);
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return null;
    }
}
