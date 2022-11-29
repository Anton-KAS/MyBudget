package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.ResponseWaitingMap;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.util.CommandControllerImpl;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */

public class StopCommand extends CommandControllerImpl {

    public StopCommand(BotMessageService botMessageService, TelegramUserService telegramUserService,
                       ExecuteMode defaultExecuteMode, MessageText messageText, Keyboard keyboard) {
        super(botMessageService, telegramUserService, defaultExecuteMode, messageText, keyboard);
    }

    @Override
    protected void setData(Update update) {
        super.setData(update);

        ResponseWaitingMap.remove(UpdateParameter.getChatId(update));

        telegramUserService.findById(UpdateParameter.getUserId(update)).ifPresent(
                user -> {
                    user.setActive(false);
                    telegramUserService.save(user);
                }
        );
    }
}
