package ru.kas.myBudget.bots.telegram.keyboards.AddAccount;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.InlineKeyboardBuilder;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.DESCRIPTION;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogName.ADD_ACCOUNT;

public class DescriptionKeyboard implements Keyboard {
    public DescriptionKeyboard() {
    }

    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addButton(getNextButton(ADD_ACCOUNT.getDialogName(), DESCRIPTION.getDialogId()))
                .build();
    }
}
