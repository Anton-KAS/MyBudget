package komrachkov.anton.mybudget.bots.telegram.keyboards.commands;

import komrachkov.anton.mybudget.bots.telegram.keyboards.util.InlineKeyboardBuilder;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackNamesImpl;
import komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackType;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class MenuKeyboard implements Keyboard {
    public final String FROM = "menu";
    public final String ACCOUNTS_BUTTON_TEXT = "Счета";
    public final String ACCOUNTS_BUTTON_CALLBACK = String.format("%s_%s_%s",
            CallbackType.NORMAL.getId(), FROM, CallbackNamesImpl.ACCOUNTS.getName());

    public MenuKeyboard() {
    }

    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addButton(ACCOUNTS_BUTTON_TEXT, ACCOUNTS_BUTTON_CALLBACK)
                .addRow().addCloseButton(FROM)
                .build();
    }
}