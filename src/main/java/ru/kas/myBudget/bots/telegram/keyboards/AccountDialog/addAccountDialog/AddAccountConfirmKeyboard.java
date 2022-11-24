package ru.kas.myBudget.bots.telegram.keyboards.AccountDialog.addAccountDialog;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.AccountDialog.ConfirmKeyboard;
import ru.kas.myBudget.bots.telegram.keyboards.util.InlineKeyboardBuilder;

import static ru.kas.myBudget.bots.telegram.dialogs.account.AccountNames.CONFIRM;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

public class AddAccountConfirmKeyboard extends ConfirmKeyboard {

    public AddAccountConfirmKeyboard(String currentDialogName) {
        super(currentDialogName);
    }

    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addSaveButton(currentDialogName, CONFIRM.getName())
                .addRow().addCancelDialogButton(currentDialogName)
                .build();
    }
}
