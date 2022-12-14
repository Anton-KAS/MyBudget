package ru.kas.myBudget.bots.telegram.keyboards.callback;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.util.InlineKeyboardBuilder;
import ru.kas.myBudget.bots.telegram.keyboards.util.Keyboard;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackNamesImpl.ACCOUNT;
import static ru.kas.myBudget.bots.telegram.callbacks.CallbackNamesImpl.ACCOUNTS;
import static ru.kas.myBudget.bots.telegram.callbacks.util.CallbackType.DIALOG;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.EDIT_ACCOUNT;
import static ru.kas.myBudget.bots.telegram.dialogs.account.AccountNames.START;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class AccountKeyboard implements Keyboard {
    public final static String FROM = ACCOUNT.getName();
    public final static String RETURN_TO = ACCOUNTS.getName();
    public final static String EDIT_ACCOUNT_BUTTON_TEXT = "Редактировать";
    public final static String EDIT_ACCOUNT_BUTTON_PATTERN = String.format("%s_%s_%s_%s_%s",
            DIALOG.getId(), ACCOUNT.getName(), EDIT_ACCOUNT.getName(), START.getName(), "%s"); // TODO: One place for keeping callbacks patterns

    public String editAccountButtonText;

    private Integer accountId;

    public AccountKeyboard setAccountId(int accountId) {
        this.accountId = accountId;
        this.editAccountButtonText = String.format(EDIT_ACCOUNT_BUTTON_PATTERN, accountId);
        return this;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addButton(EDIT_ACCOUNT_BUTTON_TEXT, editAccountButtonText)
                .addRow().addReturnButton(FROM, RETURN_TO).build();
    }
}
