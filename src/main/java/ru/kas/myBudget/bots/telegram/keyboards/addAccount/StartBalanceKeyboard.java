package ru.kas.myBudget.bots.telegram.keyboards.addAccount;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.InlineKeyboardBuilder;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountNames.START_BALANCE;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;

public class StartBalanceKeyboard implements Keyboard {
    public StartBalanceKeyboard() {
    }

    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addButton(getNextButton(ADD_ACCOUNT.getName(), START_BALANCE.getName()))
                .build();
    }
}
