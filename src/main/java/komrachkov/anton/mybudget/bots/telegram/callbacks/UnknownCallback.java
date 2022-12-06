package komrachkov.anton.mybudget.bots.telegram.callbacks;

import komrachkov.anton.mybudget.bots.telegram.keyboards.UnknownKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.UnknownText;
import komrachkov.anton.mybudget.services.TelegramUserService;
import komrachkov.anton.mybudget.bots.telegram.util.CommandControllerImpl;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Anton Komrachkov
 * @since 0.2
 * @deprecated
 */

@Component
public class UnknownCallback extends CommandControllerImpl {
    public UnknownCallback(TelegramUserService telegramUserService, UnknownText messageText, UnknownKeyboard keyboard) {
        super(telegramUserService, messageText, keyboard);
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (04.12.2022)
     */
    @Override
    public void setDefaultExecuteMode(Update update) {
        this.defaultExecuteMode = ExecuteMode.getCallbackExecuteMode();
    }
}
