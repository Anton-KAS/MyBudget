package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.keyboards.UnknownKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.UnknownText;
import komrachkov.anton.mybudget.bots.telegram.util.CommandControllerImpl;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */

@Component
public class UnknownCommand extends CommandControllerImpl {
    @Autowired
    public UnknownCommand(TelegramUserService telegramUserService, UnknownText messageText, UnknownKeyboard keyboard) {
        super(telegramUserService, messageText, keyboard);
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (04.12.2022)
     */
    @Override
    public void setDefaultExecuteMode(Update update) {
        this.defaultExecuteMode = ExecuteMode.getCommandExecuteMode();
    }
}
