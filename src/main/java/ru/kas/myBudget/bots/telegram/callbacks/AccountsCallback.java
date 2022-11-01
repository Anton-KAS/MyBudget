package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.kas.myBudget.bots.telegram.services.SendBotMessageService;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.ArrayList;
import java.util.List;

public class AccountsCallback implements Callback {

    private final SendBotMessageService sendBotMessageService;

    public final static String ACCOUNTS_MESSAGE = "Меню:";

    public AccountsCallback(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("+ Добавить");
        inlineKeyboardButton.setCallbackData("accounts_addAccount");
        rowInline.add(inlineKeyboardButton);

        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("<- Назад");
        inlineKeyboardButton2.setCallbackData("accounts_menu");
        rowInline.add(inlineKeyboardButton2);

        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);

        sendBotMessageService.editMessageWithInlineKeyboard(getChatId(update), getMessageId(update), ACCOUNTS_MESSAGE, markupInline);
    }
}
