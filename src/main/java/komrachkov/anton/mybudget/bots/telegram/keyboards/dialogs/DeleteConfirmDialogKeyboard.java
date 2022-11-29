package komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs;

import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsMap;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.InlineKeyboardBuilder;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName.*;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class DeleteConfirmDialogKeyboard implements Keyboard {
    private Update update;

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        long chatId = UpdateParameter.getChatId(update);
        String fromDialog = DialogsMap.getDialogStepById(chatId, DIALOG_ID.getId());
        String editId = DialogsMap.getDialogStepById(chatId, EDIT_ID.getId());
        String fromMenu = DialogsMap.getDialogStepById(chatId, START_FROM_ID.getId());

        return new InlineKeyboardBuilder()
                .addRow().addExecuteDeleteButton(fromDialog, editId)
                .addRow().addCancelDeleteButton(fromMenu, fromDialog, "delete")
                .build();
    }

    public DeleteConfirmDialogKeyboard setUpdate(Update update) {
        this.update = update;
        return this;
    }
}
