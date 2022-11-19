package ru.kas.myBudget.bots.telegram.keyboards.callback;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.DialogKeyboardImpl;
import ru.kas.myBudget.bots.telegram.keyboards.InlineKeyboardBuilder;

import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.CONFIRM;

public class EditAccountConfirmKeyboard extends DialogKeyboardImpl {

    public EditAccountConfirmKeyboard(String currentDialogName) {
        super(currentDialogName);
    }

    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addSaveButton(currentDialogName, CONFIRM.getName())
                .addRow().addDeleteButton(currentDialogName, CONFIRM.getName())
                .addRow().addCancelDialogButton(currentDialogName)
                .build();
    }
}
