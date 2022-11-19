package ru.kas.myBudget.bots.telegram.keyboards.addAccountDialog;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.DialogKeyboardImpl;
import ru.kas.myBudget.bots.telegram.keyboards.InlineKeyboardBuilder;

import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.DESCRIPTION;

public class DescriptionKeyboard extends DialogKeyboardImpl {
    public DescriptionKeyboard(String currentDialogName) {
        super(currentDialogName);
    }

    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addNextButton(currentDialogName, DESCRIPTION.getName())
                .build();
    }
}
