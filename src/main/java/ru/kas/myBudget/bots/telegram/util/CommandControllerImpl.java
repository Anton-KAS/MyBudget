package ru.kas.myBudget.bots.telegram.util;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.util.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.services.TelegramUserService;

public abstract class CommandControllerImpl implements CommandController{
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
        if (messageText != null) this.text = messageText.setUserId(UpdateParameter.getUserId(update)).getText();
        if (keyboard != null) this.inlineKeyboardMarkup = keyboard.getKeyboard();
    }

    protected void executeData(Update update, ExecuteMode executeMode) {
        botMessageService.executeAndUpdateUser(telegramUserService, update, executeMode, text, inlineKeyboardMarkup);
    }
}
