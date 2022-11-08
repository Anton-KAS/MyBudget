package ru.kas.myBudget.bots.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.DESCRIPTION;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogName.ADD_ACCOUNT;

public class AddDescriptionKeyboard implements Keyboard {
    public AddDescriptionKeyboard() {
    }

    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addButton(getNextButton(ADD_ACCOUNT.getDialogName(), DESCRIPTION.getDialogId()))
                .build();
    }
}
