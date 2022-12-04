package komrachkov.anton.mybudget.bots.telegram.dialogs.account.edit;

import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.AccountDialogText;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.ConfirmDialog;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.edit.EditConfirmKeyboard;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class EditConfirmDialog extends ConfirmDialog {
    private final EditConfirmKeyboard editConfirmKeyboard;

    @Autowired
    public EditConfirmDialog(TelegramUserService telegramUserService, AccountDialogText messageText, EditConfirmKeyboard keyboard) {
        super(telegramUserService, messageText, keyboard);
        this.editConfirmKeyboard = keyboard;
    }

    @Override
    public ToDoList execute(Update update, ExecuteMode executeMode) {
        editConfirmKeyboard.setChatId(UpdateParameter.getChatId(update));
        return super.execute(update, executeMode);
    }
}
