package komrachkov.anton.mybudget.bots.telegram.callbacks;

import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.util.CommandControllerImpl;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.ResponseWaitingMap;

/**
 * Закрытие текущего окна меню (удаление сообщения с меню)
 *
 * @author Anton Komrachkov
 * @since 0.2
 */

public class CloseCallback extends CommandControllerImpl {

    public CloseCallback(BotMessageService botMessageService, TelegramUserService telegramUserService,
                         ExecuteMode defaultExecuteMode, MessageText messageText, Keyboard keyboard) {
        super(botMessageService, telegramUserService, defaultExecuteMode, messageText, keyboard);
    }

    @Override
    protected void executeData(Update update, ExecuteMode executeMode) {
        long chatId = UpdateParameter.getChatId(update);
        botMessageService.deleteMessage(chatId, UpdateParameter.getMessageId(update));
        botMessageService.updateUser(telegramUserService, update);
        ResponseWaitingMap.remove(chatId);
    }
}
