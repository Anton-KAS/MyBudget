package ru.kas.myBudget.bots.telegram.keyboards.addAccountDialog;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.InlineKeyboardBuilder;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackType.*;
import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountNames.CONFIRM;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;

public class ConfirmKeyboard implements Keyboard {
    public final String SAVE_BUTTON_TEXT = "Сохранить";
    public final String SAVE_BUTTON_CALLBACK = String.format("%s_%s_%s_%s_%s",
            DIALOG.getId(), ADD_ACCOUNT.getName(), ADD_ACCOUNT.getName(), CONFIRM.getName(), "save");

    public ConfirmKeyboard() {
    }

    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addButton(SAVE_BUTTON_TEXT, SAVE_BUTTON_CALLBACK)
                .addRow().addButton(getCancelDialogButton(ADD_ACCOUNT.getName()))
                .build();
    }
}
