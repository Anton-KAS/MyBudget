package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.keyboards.commands.MenuKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.MenuText;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import komrachkov.anton.mybudget.bots.telegram.util.CommandControllerImpl;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */

@Component
public class MenuCommand extends CommandControllerImpl {
    @Autowired
    public MenuCommand(TelegramUserService telegramUserService, MenuText messageText, MenuKeyboard keyboard) {
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
