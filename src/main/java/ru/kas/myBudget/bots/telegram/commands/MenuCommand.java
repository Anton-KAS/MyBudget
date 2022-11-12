package ru.kas.myBudget.bots.telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.TelegramUserService;

public class MenuCommand implements CommandController {
    private final ExecuteMode defaultExecuteMode;
    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;
    private final Keyboard keyboard;
    private final MessageText messageText;

    public MenuCommand(BotMessageService botMessageService, TelegramUserService telegramUserService,
                       ExecuteMode defaultExecuteMode, Keyboard keyboard, MessageText messageText) {
        this.defaultExecuteMode = defaultExecuteMode;
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
        this.keyboard = keyboard;
        this.messageText = messageText;
    }

    @Override
    public void execute(Update update) {
        executeMessageService(update, defaultExecuteMode);
    }

    @Override
    public void execute(Update update, ExecuteMode executeMode) {
        executeMessageService(update, executeMode);
    }

    private void executeMessageService(Update update, ExecuteMode executeMode) {
        String text = messageText.setUserId(UpdateParameter.getUserId(update)).getText();
        InlineKeyboardMarkup inlineKeyboardMarkup = keyboard.getKeyboard();

        botMessageService.executeAndUpdateUser(telegramUserService, update, executeMode, text, inlineKeyboardMarkup);
    }
}
