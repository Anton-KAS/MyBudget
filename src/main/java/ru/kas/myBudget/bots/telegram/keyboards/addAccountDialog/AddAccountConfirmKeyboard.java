package ru.kas.myBudget.bots.telegram.keyboards.addAccountDialog;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.DialogKeyboardImpl;
import ru.kas.myBudget.bots.telegram.keyboards.InlineKeyboardBuilder;

import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.CONFIRM;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;

public class AddAccountConfirmKeyboard extends DialogKeyboardImpl {
    public final String SAVE_BUTTON_TEXT = "Сохранить";

    public AddAccountConfirmKeyboard(String currentDialogName) {
        super(currentDialogName);
        this.callbackPattern = String.format(callbackPattern, CONFIRM.getName(), "save");
    }

    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addButton(SAVE_BUTTON_TEXT, callbackPattern)
                .addRow().addButton(getCancelDialogButton(ADD_ACCOUNT.getName()))
                .build();
    }
}
