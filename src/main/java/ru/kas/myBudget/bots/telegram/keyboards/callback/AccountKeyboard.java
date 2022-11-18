package ru.kas.myBudget.bots.telegram.keyboards.callback;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.InlineKeyboardBuilder;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackNamesImpl.ACCOUNT;
import static ru.kas.myBudget.bots.telegram.callbacks.CallbackNamesImpl.ACCOUNTS;
import static ru.kas.myBudget.bots.telegram.callbacks.CallbackType.DIALOG;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.EDIT_ACCOUNT;
import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.START;

public class AccountKeyboard implements Keyboard {
    public final String FROM = ACCOUNT.getName();
    public final String RETURN_TO = ACCOUNTS.getName();
    public final String EDIT_ACCOUNT_BUTTON_TEXT = "Редактировать";
    public String EDIT_ACCOUNT_BUTTON_CALLBACK = String.format("%s_%s_%s_%s_%s",
            DIALOG.getId(), ACCOUNT.getName(), EDIT_ACCOUNT.getName(), START.getName(), "%s"); // TODO: One place for keeping callbacks patterns

    private Integer accountId;

    public AccountKeyboard setAccountId(int accountId) {
        this.accountId = accountId;
        this.EDIT_ACCOUNT_BUTTON_CALLBACK = String.format(EDIT_ACCOUNT_BUTTON_CALLBACK, accountId);
        return this;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addButton(EDIT_ACCOUNT_BUTTON_TEXT, EDIT_ACCOUNT_BUTTON_CALLBACK)
                .addRow().addButton(getReturnButton(FROM, RETURN_TO)).build();
    }
}
