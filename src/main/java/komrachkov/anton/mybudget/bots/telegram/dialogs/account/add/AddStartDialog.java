package komrachkov.anton.mybudget.bots.telegram.dialogs.account.add;

import komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogIndex;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.StartDialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackNamesImpl.MENU;
import static komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackType.NORMAL;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class AddStartDialog extends StartDialog {

    public AddStartDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
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

        DialogsState.put(chatId, DialogMapDefaultName.START_FROM_CALLBACK.getId(),
                String.format("%s_%s_%s", NORMAL.getId(), MENU.getName(), callbackData[CallbackIndex.FROM.ordinal()]));
        DialogsState.put(chatId, DialogMapDefaultName.CURRENT_DIALOG_STEP.getId(),
                String.valueOf(DialogIndex.FIRST_STEP_INDEX.ordinal()));
        DialogsState.put(chatId, DialogMapDefaultName.LAST_STEP.getId(),
                String.valueOf(DialogIndex.FIRST_STEP_INDEX.ordinal()));
        DialogsState.put(chatId, DialogMapDefaultName.CAN_SAVE.getId(), "false");
        return true;
    }
}
