package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.MenuKeyboard;
import ru.kas.myBudget.bots.telegram.services.SendBotMessageService;
import ru.kas.myBudget.services.TelegramUserService;

public class MenuCallback implements Callback {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public final static String MENU_MESSAGE = "Меню:";

    public MenuCallback(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        InlineKeyboardMarkup markupInline = new MenuKeyboard().getKeyboard();

        sendBotMessageService.editMessageWithInlineKeyboard(getChatId(update), getMessageId(update), MENU_MESSAGE, markupInline);
        updateUserLastActiveInDb(telegramUserService, update);
    }
}
