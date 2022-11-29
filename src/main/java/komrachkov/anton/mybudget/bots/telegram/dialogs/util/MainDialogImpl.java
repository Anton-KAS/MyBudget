package komrachkov.anton.mybudget.bots.telegram.dialogs.util;

import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public abstract class MainDialogImpl implements MainDialog {
    protected final BotMessageService botMessageService;
    protected final TelegramUserService telegramUserService;

    public MainDialogImpl(BotMessageService botMessageService, TelegramUserService telegramUserService) {
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update, ExecuteMode executeMode) {
        execute(update);
    }

}
