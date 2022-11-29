package komrachkov.anton.mybudget.bots.telegram.callbacks;

import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsMap;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.util.CommandControllerImpl;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.ResponseWaitingMap;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackNamesImpl.MENU;
import static komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex.TO;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName.START_FROM_CALLBACK;

/**
 * Отмена и сброс диалога и ожидания ввода от пользователя, возврат к стартовой точке меню
 *
 * @author Anton Komrachkov
 * @version 1.0
 */


public class CancelDialogCallback extends CommandControllerImpl {
    private final CallbackContainer callbackContainer;

    public CancelDialogCallback(BotMessageService botMessageService, TelegramUserService telegramUserService,
                                ExecuteMode defaultExecuteMode, MessageText messageText, Keyboard keyboard,
                                CallbackContainer callbackContainer) {
        super(botMessageService, telegramUserService, defaultExecuteMode, messageText, keyboard);
        this.callbackContainer = callbackContainer;
    }

    @Override
    protected void executeData(Update update, ExecuteMode executeMode) {
        botMessageService.sendPopup(UpdateParameter.getCallbackQueryId(update).orElse(null), text);

        long chatId = UpdateParameter.getChatId(update);
        if (update.hasCallbackQuery()) {
            update.getCallbackQuery().setData(DialogsMap.getDialogStepById(chatId, START_FROM_CALLBACK.getId()));
            String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);
            String identifier;
            if (callbackData != null && callbackData.length > TO.ordinal()) {
                identifier = callbackData[TO.ordinal()];
            } else {
                identifier = MENU.getName();
            }
            callbackContainer.retrieve(identifier).execute(update);
        }
        ResponseWaitingMap.remove(chatId);
        DialogsMap.remove(chatId);
    }
}
