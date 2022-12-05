package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.keyboards.commands.HelpKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.HelpText;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.services.TelegramUserService;
import komrachkov.anton.mybudget.bots.telegram.util.CommandControllerImpl;
import org.checkerframework.checker.guieffect.qual.UIPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */

@Component
public class HelpCommand extends CommandControllerImpl {
    @Autowired
    public HelpCommand(TelegramUserService telegramUserService, HelpText messageText, HelpKeyboard keyboard) {
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
