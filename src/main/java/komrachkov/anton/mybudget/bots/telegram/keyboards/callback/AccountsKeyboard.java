package komrachkov.anton.mybudget.bots.telegram.keyboards.callback;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.InlineKeyboardBuilder;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackNamesImpl.ACCOUNTS;
import static komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackNamesImpl.MENU;
import static komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackType.DIALOG;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.START;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class AccountsKeyboard implements Keyboard {
    public final static String FROM = ACCOUNTS.getName();
    public final static String RETURN_TO = MENU.getName();
    public final static String ADD_ACCOUNT_BUTTON_TEXT = "+ Добавить";
    public final static String ADD_ACCOUNT_BUTTON_CALLBACK = String.format("%s_%s_%s_%s_start",
            DIALOG.getId(), ACCOUNTS.getName(), ADD_ACCOUNT.getName(), START.getName()); // TODO: One place for keeping callbacks patterns

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addButton(ADD_ACCOUNT_BUTTON_TEXT, ADD_ACCOUNT_BUTTON_CALLBACK)
                .addRow().addReturnButton(FROM, RETURN_TO).build();
    }
}
