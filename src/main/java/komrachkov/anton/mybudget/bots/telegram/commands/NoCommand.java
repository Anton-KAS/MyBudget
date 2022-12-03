package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.commands.util.CommandControllerTestImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.commands.NoKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.NoText;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */

@Component
public class NoCommand extends CommandControllerTestImpl {
    @Autowired
    public NoCommand(TelegramUserService telegramUserService, NoText messageText, NoKeyboard keyboard) {
        super(telegramUserService, messageText, keyboard);
    }
}
