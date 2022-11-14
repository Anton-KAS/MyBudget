package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

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
import java.util.Map;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackIndex.FROM;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.FIRST_STEP_INDEX;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.*;

public class StartDialog extends DialogImpl {

    public StartDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                       MessageText messageText, Keyboard keyboard, DialogsMap dialogsMap) {
        super(botMessageService, telegramUserService, messageText, keyboard, dialogsMap, null);
    }

    @Override
    public void executeByOrder(Update update, ExecuteMode executeMode) {
    }

    @Override
    public boolean commit(Update update) {
        this.userId = UpdateParameter.getUserId(update);
        String[] callbackData = UpdateParameter.getCallbackData(update);
        dialogsMap.remove(userId);

        if (callbackData == null) return false;
        Map<String, String> dialogSteps = new HashMap<>();

        dialogSteps.put(DIALOG_ID.getId(), DialogNamesImpl.ADD_ACCOUNT.getName());
        dialogSteps.put(START_FROM_ID.getId(), callbackData[FROM.getIndex()]);
        dialogSteps.put(CURRENT_DIALOG_STEP.getId(), String.valueOf(FIRST_STEP_INDEX.getIndex()));
        dialogSteps.put(LAST_STEP.getId(), String.valueOf(FIRST_STEP_INDEX.getIndex()));

        dialogsMap.putDialogMap(userId, dialogSteps);
        return true;
    }
}
