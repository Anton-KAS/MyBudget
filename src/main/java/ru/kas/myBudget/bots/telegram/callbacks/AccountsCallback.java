package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.AccountsKeyboard;
import ru.kas.myBudget.bots.telegram.services.SendBotMessageService;
import ru.kas.myBudget.bots.telegram.texts.AccountsText;
import ru.kas.myBudget.services.AccountService;
import ru.kas.myBudget.services.TelegramUserService;

public class AccountsCallback implements Callback {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final AccountService accountService;

    public AccountsCallback(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, AccountService accountService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.accountService = accountService;
    }

    @Override
    public void execute(Update update) {
        InlineKeyboardMarkup markupInline = new AccountsKeyboard().getKeyboard();
        String text = new AccountsText().getText(telegramUserService, getUserId(update));

        sendBotMessageService.editMessageWithInlineKeyboard(getChatId(update), getMessageId(update), text, markupInline);
        updateUserLastActiveInDb(telegramUserService, update);
    }
}
