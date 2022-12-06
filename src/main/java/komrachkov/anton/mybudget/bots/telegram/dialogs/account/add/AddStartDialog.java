package komrachkov.anton.mybudget.bots.telegram.dialogs.account.add;

import komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogIndex;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName;
import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.AccountDialogText;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.StartDialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackNamesImpl.MENU;
import static komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackType.NORMAL;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class AddStartDialog extends StartDialog {

    @Autowired
    public AddStartDialog(TelegramUserService telegramUserService, AccountDialogText messageText) {
        super(telegramUserService, messageText);
    }

    @Override
    public ToDoList commit(Update update) {
        ToDoList toDoList = super.commit(update);
        if (!toDoList.isResultCommit()) return toDoList;
        long chatId = UpdateParameter.getChatId(update);

        DialogsState.put(chatId, DialogMapDefaultName.START_FROM_CALLBACK.getId(),
                String.format("%s_%s_%s", NORMAL.getId(), MENU.getName(), callbackData[CallbackIndex.FROM.ordinal()]));
        DialogsState.put(chatId, DialogMapDefaultName.CURRENT_DIALOG_STEP.getId(),
                String.valueOf(DialogIndex.FIRST_STEP_INDEX.ordinal()));
        DialogsState.put(chatId, DialogMapDefaultName.LAST_STEP.getId(),
                String.valueOf(DialogIndex.FIRST_STEP_INDEX.ordinal()));
        DialogsState.put(chatId, DialogMapDefaultName.CAN_SAVE.getId(), "false");

        toDoList.setResultCommit(true);
        return toDoList;
    }
}
