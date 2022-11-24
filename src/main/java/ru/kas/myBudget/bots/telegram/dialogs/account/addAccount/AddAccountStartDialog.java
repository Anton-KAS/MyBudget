package ru.kas.myBudget.bots.telegram.dialogs.account.addAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.account.StartDialog;
import ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl;
import ru.kas.myBudget.bots.telegram.dialogs.util.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.util.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Map;

import static ru.kas.myBudget.bots.telegram.callbacks.util.CallbackIndex.FROM;
import static ru.kas.myBudget.bots.telegram.callbacks.CallbackNamesImpl.MENU;
import static ru.kas.myBudget.bots.telegram.callbacks.util.CallbackType.NORMAL;
import static ru.kas.myBudget.bots.telegram.dialogs.util.DialogIndex.FIRST_STEP_INDEX;
import static ru.kas.myBudget.bots.telegram.dialogs.util.DialogMapDefaultName.*;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

public class AddAccountStartDialog extends StartDialog {

    public AddAccountStartDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                                 MessageText messageText, Keyboard keyboard,
                                 DialogNamesImpl dialogName) {
        super(botMessageService, telegramUserService, messageText, keyboard, dialogName);
    }

    @Override
    public void executeByOrder(Update update, ExecuteMode executeMode) {
    }

    @Override
    public boolean commit(Update update) {
        if (!super.commit(update)) return false;

        Map<String, String> dialogMap = DialogsMap.getDialogMapById(chatId);

        dialogMap.put(START_FROM_CALLBACK.getId(), String.format("%s_%s_%s", NORMAL.getId(), MENU.getName(),
                callbackData[FROM.ordinal()]));
        dialogMap.put(CURRENT_DIALOG_STEP.getId(), String.valueOf(FIRST_STEP_INDEX.ordinal()));
        dialogMap.put(LAST_STEP.getId(), String.valueOf(FIRST_STEP_INDEX.ordinal()));
        dialogMap.put(CAN_SAVE.getId(), "false");
        return true;
    }
}
