package ru.kas.myBudget.bots.telegram.keyboards.accountDialog;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.DialogKeyboardImpl;
import ru.kas.myBudget.bots.telegram.keyboards.util.InlineKeyboardBuilder;

import static ru.kas.myBudget.bots.telegram.dialogs.account.AccountNames.DESCRIPTION;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class DescriptionKeyboard extends DialogKeyboardImpl {
    public DescriptionKeyboard(String currentDialogName) {
        super(currentDialogName);
    }

    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addNextButton(currentDialogName, DESCRIPTION.getName())
                .build();
    }
}
