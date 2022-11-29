package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.StatText;
import komrachkov.anton.mybudget.bots.telegram.util.CommandControllerImpl;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */

public class StatCommand extends CommandControllerImpl {
    public StatCommand(BotMessageService botMessageService, TelegramUserService telegramUserService,
                       ExecuteMode defaultExecuteMode, MessageText messageText, Keyboard keyboard) {
        super(botMessageService, telegramUserService, defaultExecuteMode, messageText, keyboard);
    }

    @Override
    protected void setData(Update update) {
        int activeUserCount = telegramUserService.retrieveAllActiveUsers().size();
        StatText statText = (StatText) messageText;
        this.text = statText.setUserId(UpdateParameter.getUserId(update)).setActiveUserCount(activeUserCount).getText();
        this.inlineKeyboardMarkup = keyboard.getKeyboard();
    }
}
