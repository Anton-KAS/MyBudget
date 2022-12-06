package komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs;

import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.InlineKeyboardBuilder;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName.*;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class DeleteConfirmDialogKeyboard implements Keyboard {
    private Update update;

    @Override
    public InlineKeyboardMarkup getKeyboard() {

        long chatId = UpdateParameter.getChatId(update);

        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();

        String fromDialog = DialogsState.getDialogStepById(chatId, DIALOG_ID.getId()).orElse(null);
        String editId = DialogsState.getDialogStepById(chatId, EDIT_ID.getId()).orElse(null);
        if (fromDialog != null && editId != null)
            inlineKeyboardBuilder.addRow().addExecuteDeleteButton(fromDialog, editId);

        String fromMenu = DialogsState.getDialogStepById(chatId, START_FROM_ID.getId()).orElse(null);
        if (fromMenu != null && fromDialog != null)
            inlineKeyboardBuilder.addRow().addCancelDeleteButton(fromMenu, fromDialog, "delete"); // TODO: "Magic word"

        return inlineKeyboardBuilder.build();
    }

    public DeleteConfirmDialogKeyboard setUpdate(Update update) {
        this.update = update;
        return this;
    }
}
