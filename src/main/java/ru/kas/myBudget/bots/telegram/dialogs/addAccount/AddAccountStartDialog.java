package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.DialogImpl;
import ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.HashMap;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackIndex.FROM;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.FIRST_STEP_INDEX;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.*;

public class AddAccountStartDialog extends DialogImpl {
    protected final DialogNamesImpl dialogName;
    protected Long chatId;
    protected String[] callbackData;

    public AddAccountStartDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                                 MessageText messageText, Keyboard keyboard, DialogsMap dialogsMap,
                                 DialogNamesImpl dialogName) {
        super(botMessageService, telegramUserService, messageText, keyboard, dialogsMap, null);
        this.dialogName = dialogName;
    }

    @Override
    public void executeByOrder(Update update, ExecuteMode executeMode) {
    }

    @Override
    public boolean commit(Update update) {
        chatId = UpdateParameter.getChatId(update);
        callbackData = UpdateParameter.getCallbackData(update);
        dialogsMap.remove(chatId);

        if (callbackData == null) return false;
        var dialogSteps = new HashMap<String, String>();

        if (callbackData.length <= FROM.getIndex()) return false;

        dialogSteps.put(DIALOG_ID.getId(), dialogName.getName());
        dialogSteps.put(START_FROM_ID.getId(), callbackData[FROM.getIndex()]);
        dialogSteps.put(CURRENT_DIALOG_STEP.getId(), String.valueOf(FIRST_STEP_INDEX.getIndex()));
        dialogSteps.put(LAST_STEP.getId(), String.valueOf(FIRST_STEP_INDEX.getIndex()));

        dialogsMap.putDialogMap(chatId, dialogSteps);
        return true;
    }
}
