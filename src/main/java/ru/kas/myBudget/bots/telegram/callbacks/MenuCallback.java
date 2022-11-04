package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.MenuKeyboard;
import ru.kas.myBudget.bots.telegram.services.SendBotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MenuText;
import ru.kas.myBudget.services.TelegramUserService;

public class MenuCallback implements Callback {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public MenuCallback(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        String text = new MenuText().getText(telegramUserService, getUserId(update));
        InlineKeyboardMarkup markupInline = new MenuKeyboard().getKeyboard();

        sendBotMessageService.editMessageWithInlineKeyboard(getChatId(update), getMessageId(update), text, markupInline);
        updateUserLastActiveInDb(telegramUserService, update);
    }
}
