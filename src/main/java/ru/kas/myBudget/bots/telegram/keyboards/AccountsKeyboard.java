package ru.kas.myBudget.bots.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackNamesImpl.ACCOUNTS;
import static ru.kas.myBudget.bots.telegram.callbacks.CallbackType.DIALOG;
import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountNames.START;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;

public class AccountsKeyboard implements Keyboard {
    public final String FROM = "account";
    public final String RETURN_TO = "menu";
    public final String ADD_ACCOUNT_BUTTON_TEXT = "+ Добавить";
    public final String ADD_ACCOUNT_BUTTON_CALLBACK = String.format("%s_%s_%s_%s_start",
            DIALOG.getId(), ACCOUNTS.getName(), ADD_ACCOUNT.getName(), START.getName()); // TODO: One place for keeping callbacks patterns

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addButton(ADD_ACCOUNT_BUTTON_TEXT, ADD_ACCOUNT_BUTTON_CALLBACK)
                .addRow().addButton(getReturnButton(FROM, RETURN_TO)).build();
    }
}
