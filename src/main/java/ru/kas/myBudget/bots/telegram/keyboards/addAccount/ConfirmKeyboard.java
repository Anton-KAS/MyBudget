package ru.kas.myBudget.bots.telegram.keyboards.addAccount;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.InlineKeyboardBuilder;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackType.*;
import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.CONFIRM;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogName.ADD_ACCOUNT;

public class ConfirmKeyboard implements Keyboard {
    public final String SAVE_BUTTON_TEXT = "Сохранить";
    public final String SAVE_BUTTON_CALLBACK = String.format("%s_%s_%s_%s_%s",
            DIALOG.getId(), ADD_ACCOUNT.getDialogName(), ADD_ACCOUNT.getDialogName(), CONFIRM.getDialogId(), "save");

    public ConfirmKeyboard() {
    }

    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addButton(SAVE_BUTTON_TEXT, SAVE_BUTTON_CALLBACK)
                .addRow().addButton(getCancelDialogButton(ADD_ACCOUNT.getDialogName()))
                .build();
    }
}
