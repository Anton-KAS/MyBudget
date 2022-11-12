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

public class NoCommand implements CommandController {
    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;
    private final MessageText messageText;
    private final Keyboard keyboard;

    public NoCommand(BotMessageService botMessageService, TelegramUserService telegramUserService,
                     MessageText messageText, Keyboard keyboard) {
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
        this.messageText = messageText;
        this.keyboard = keyboard;
    }

    @Override
    public void execute(Update update) {
        String text = messageText.setUserId(UpdateParameter.getUserId(update)).getText();
        InlineKeyboardMarkup inlineKeyboardMarkup = keyboard.getKeyboard();

        botMessageService.executeAndUpdateUser(telegramUserService, update, ExecuteMode.SEND, text, inlineKeyboardMarkup);
    }

    @Override
    public void execute(Update update, ExecuteMode executeMode) {
        execute(update);
    }
}
