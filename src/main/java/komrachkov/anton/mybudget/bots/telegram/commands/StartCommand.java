package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.keyboards.commands.StartKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.StartText;
import komrachkov.anton.mybudget.bots.telegram.util.CommandControllerImpl;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */

@Component
public class StartCommand extends CommandControllerImpl {
    @Autowired
    public StartCommand(TelegramUserService telegramUserService, StartText messageText, StartKeyboard keyboard) {
        super(telegramUserService, messageText, keyboard);
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (04.12.2022)
     */
    @Override
    public void setDefaultExecuteMode() {
        this.defaultExecuteMode = ExecuteMode.getCommandExecuteMode();
    }
}
