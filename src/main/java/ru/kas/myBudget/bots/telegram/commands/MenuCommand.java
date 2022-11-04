package ru.kas.myBudget.bots.telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.MenuKeyboard;
import ru.kas.myBudget.bots.telegram.services.SendBotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MenuText;
import ru.kas.myBudget.services.TelegramUserService;

public class MenuCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public MenuCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        String text = new MenuText().getText(telegramUserService, getUserId(update));
        InlineKeyboardMarkup markupInline = new MenuKeyboard().getKeyboard();

        sendBotMessageService.sendMessageWithInlineKeyboard(getChatId(update), text, markupInline);
        checkUserInDb(telegramUserService, update);
    }
}
