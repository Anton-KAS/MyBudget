package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.ResponseWaitingMap;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.util.CommandControllerImpl;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */

public class MenuCommand extends CommandControllerImpl {

    public MenuCommand(BotMessageService botMessageService, TelegramUserService telegramUserService,
                       ExecuteMode defaultExecuteMode, MessageText messageText, Keyboard keyboard) {
        super(botMessageService, telegramUserService, defaultExecuteMode, messageText, keyboard);
    }

    @Override
    public void executeByOrder(Update update, ExecuteMode executeMode) {
        super.executeByOrder(update, executeMode);
        ResponseWaitingMap.remove(UpdateParameter.getChatId(update));
    }
}
