package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex;
import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public abstract class ConfirmDialog extends DialogImpl {
    protected final static String ASK_TEXT = "Всё готово! Сохранить?";
    protected final static String VERIFY_EXCEPTION_TEXT = "В этом диалоге такая комманда не поддерживается";
    protected String[] callbackData;

    public ConfirmDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                         MessageText messageText, Keyboard keyboard) {
        super(botMessageService, telegramUserService, messageText, keyboard, ASK_TEXT);
    }

    @Override
    public boolean commit(Update update) {
        this.callbackData = UpdateParameter.getCallbackData(update).orElse(null);
        if (callbackData == null || callbackData.length <= CallbackIndex.OPERATION_DATA.ordinal()
                || !callbackData[CallbackIndex.OPERATION_DATA.ordinal()].equals("save")) {
            botMessageService.executeAndUpdateUser(telegramUserService, update, ExecuteMode.SEND,
                    VERIFY_EXCEPTION_TEXT, null);
            return false;
        }
        return true;
    }
}
