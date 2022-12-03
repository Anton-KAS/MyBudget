package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.commands.util.CommandControllerTestImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.commands.HelpKeyboard;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.HelpText;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.services.TelegramUserService;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.util.CommandControllerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */

@Component
public class HelpCommand extends CommandControllerTestImpl {
    @Autowired
    public HelpCommand(TelegramUserService telegramUserService, HelpText messageText, HelpKeyboard keyboard) {
        super(telegramUserService, messageText, keyboard);
    }
}
