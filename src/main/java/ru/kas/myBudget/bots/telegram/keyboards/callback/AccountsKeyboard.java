package ru.kas.myBudget.bots.telegram.keyboards.callback;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.InlineKeyboardBuilder;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackNamesImpl.ACCOUNTS;
import static ru.kas.myBudget.bots.telegram.callbacks.CallbackNamesImpl.MENU;
import static ru.kas.myBudget.bots.telegram.callbacks.CallbackType.DIALOG;
import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.START;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;

public class AccountsKeyboard implements Keyboard {
    public final String FROM = ACCOUNTS.getName();
    public final String RETURN_TO = MENU.getName();
    public final String ADD_ACCOUNT_BUTTON_TEXT = "+ Добавить";
    public final String ADD_ACCOUNT_BUTTON_CALLBACK = String.format("%s_%s_%s_%s_start",
            DIALOG.getId(), ACCOUNTS.getName(), ADD_ACCOUNT.getName(), START.getName()); // TODO: One place for keeping callbacks patterns

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addButton(ADD_ACCOUNT_BUTTON_TEXT, ADD_ACCOUNT_BUTTON_CALLBACK)
                .addRow().addReturnButton(FROM, RETURN_TO).build();
    }
}
