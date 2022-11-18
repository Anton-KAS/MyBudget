package ru.kas.myBudget.bots.telegram.keyboards.callback;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.InlineKeyboardBuilder;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackType.DIALOG;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.EDIT_ACCOUNT;
import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.CONFIRM;

public class EditAccountConfirmKeyboard implements Keyboard {
    public final String SAVE_BUTTON_TEXT = "Сохранить";
    public final String SAVE_BUTTON_CALLBACK = String.format("%s_%s_%s_%s_%s",
            DIALOG.getId(), EDIT_ACCOUNT.getName(), EDIT_ACCOUNT.getName(), CONFIRM.getName(), "save");

    public EditAccountConfirmKeyboard() {
    }

    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addButton(SAVE_BUTTON_TEXT, SAVE_BUTTON_CALLBACK)
                .addRow().addButton(getCancelDialogButton(ADD_ACCOUNT.getName()))
                .build();
    }
}
