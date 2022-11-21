package ru.kas.myBudget.bots.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;

import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.DIALOG_ID;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.EDIT_ID;

public class DeleteConfirmDialogKeyboard implements Keyboard {
    private Update update;

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        long chatId = UpdateParameter.getChatId(update);
        String fromDialog = DialogsMap.getDialogStepById(chatId, DIALOG_ID.getId());
        String editId = DialogsMap.getDialogStepById(chatId, EDIT_ID.getId());

        return new InlineKeyboardBuilder()
                .addRow().addExecuteDeleteButton(fromDialog, editId)
                .addRow().addCancelDeleteButton(fromDialog, "delete")
                .build();
    }

    public DeleteConfirmDialogKeyboard setUpdate(Update update) {
        this.update = update;
        return this;
    }
}
