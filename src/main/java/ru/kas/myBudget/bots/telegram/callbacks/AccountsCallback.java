package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.kas.myBudget.bots.telegram.services.SendBotMessageService;
import ru.kas.myBudget.models.Account;
import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountsCallback implements Callback {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public String ACCOUNTS_MESSAGE = "Счета:";

    public AccountsCallback(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
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

        List<Account> accounts = new ArrayList<>();
        Optional<TelegramUser> telegramUser = telegramUserService.findById(getUserId(update));
        if (telegramUser.isPresent()) {
            accounts = telegramUser.get().getAccounts();
            if (accounts.isEmpty()) {
                ACCOUNTS_MESSAGE += "\nНет счетов";
            } else {
                for (Account account : accounts) {
                    ACCOUNTS_MESSAGE += "\n" + account.getTitle() + " " + account.getDescription();
                }
            }
        }
        sendBotMessageService.editMessageWithInlineKeyboard(getChatId(update), getMessageId(update), ACCOUNTS_MESSAGE, markupInline);
    }
}
