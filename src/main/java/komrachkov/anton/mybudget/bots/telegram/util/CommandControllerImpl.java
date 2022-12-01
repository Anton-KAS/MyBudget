package komrachkov.anton.mybudget.bots.telegram.util;

import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public abstract class CommandControllerImpl implements CommandController {
    protected final BotMessageService botMessageService;
    protected final TelegramUserService telegramUserService;
    protected final ExecuteMode defaultExecuteMode;
    protected final MessageText messageText;
    protected final Keyboard keyboard;

    protected String text;
    protected InlineKeyboardMarkup inlineKeyboardMarkup;

    public CommandControllerImpl(BotMessageService botMessageService, TelegramUserService telegramUserService,
                                 ExecuteMode defaultExecuteMode, MessageText messageText, Keyboard keyboard) {
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
        this.defaultExecuteMode = defaultExecuteMode;
        this.messageText = messageText;
        this.keyboard = keyboard;
    }

    @Override
    public void execute(Update update) {
        executeByOrder(update, defaultExecuteMode);
    }

    @Override
    public void execute(Update update, ExecuteMode executeMode) {
        executeByOrder(update, executeMode);
    }

    protected void executeByOrder(Update update, ExecuteMode executeMode) {
        setData(update);
        executeData(update, executeMode);
    }

    protected void setData(Update update) {
        if (messageText != null) this.text = messageText.setChatId(UpdateParameter.getChatId(update)).getText();
        if (keyboard != null) this.inlineKeyboardMarkup = keyboard.getKeyboard();
    }

    protected void executeData(Update update, ExecuteMode executeMode) {
        botMessageService.executeAndUpdateUser(telegramUserService, update, executeMode, text, inlineKeyboardMarkup);
    }
}
