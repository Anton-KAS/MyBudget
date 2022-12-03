package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.commands.util.CommandControllerTestImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.commands.UnknownKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.UnknownText;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */

@Component
public class UnknownCommand extends CommandControllerTestImpl {
    @Autowired
    public UnknownCommand(TelegramUserService telegramUserService, UnknownText messageText, UnknownKeyboard keyboard) {
        super(telegramUserService, messageText, keyboard);
    }
}
