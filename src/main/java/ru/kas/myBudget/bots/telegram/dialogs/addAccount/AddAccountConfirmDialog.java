package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.DialogImpl;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.TelegramUserService;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackIndex.OPERATION_DATA;

public class AddAccountConfirmDialog extends DialogImpl {
    private final static String ASK_TEXT = "Всё готово! Сохранить?";
    private final static String VERIFY_EXCEPTION_TEXT = "В этом диалоге такая комманда не поддерживается";

    public AddAccountConfirmDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                                   MessageText messageText, Keyboard keyboard) {
        super(botMessageService, telegramUserService, messageText, keyboard, ASK_TEXT);
    }

    @Override
    public boolean commit(Update update) {
        String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);
        if (callbackData == null || callbackData.length <= OPERATION_DATA.getIndex()
                || !callbackData[OPERATION_DATA.getIndex()].equals("save")) {
            botMessageService.executeAndUpdateUser(telegramUserService, update, ExecuteMode.SEND,
                    VERIFY_EXCEPTION_TEXT, null);
            return false;
        }
        return true;
    }
}
