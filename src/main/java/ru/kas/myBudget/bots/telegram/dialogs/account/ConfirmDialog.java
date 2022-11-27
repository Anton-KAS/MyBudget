package ru.kas.myBudget.bots.telegram.dialogs.account;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.DialogImpl;
import ru.kas.myBudget.bots.telegram.keyboards.util.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.TelegramUserService;

import static ru.kas.myBudget.bots.telegram.callbacks.util.CallbackIndex.OPERATION_DATA;

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
        if (callbackData == null || callbackData.length <= OPERATION_DATA.ordinal()
                || !callbackData[OPERATION_DATA.ordinal()].equals("save")) {
            botMessageService.executeAndUpdateUser(telegramUserService, update, ExecuteMode.SEND,
                    VERIFY_EXCEPTION_TEXT, null);
            return false;
        }
        return true;
    }
}
