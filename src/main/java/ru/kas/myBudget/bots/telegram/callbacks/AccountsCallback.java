package ru.kas.myBudget.bots.telegram.callbacks;

import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.kas.myBudget.bots.telegram.services.SendBotMessageService;
import ru.kas.myBudget.models.Account;
import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.services.AccountService;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountsCallback implements Callback {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final AccountService accountService;

    public final static String ACCOUNTS_MESSAGE = "Счета:";
    public String sendMessage = ACCOUNTS_MESSAGE;

    public AccountsCallback(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, AccountService accountService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.accountService = accountService;
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

        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("<- Назад");
        inlineKeyboardButton2.setCallbackData("accounts_menu");
        rowInline2.add(inlineKeyboardButton2);

        rowsInline.add(rowInline);
        rowsInline.add(rowInline2);
        markupInline.setKeyboard(rowsInline);

        sendMessage = ACCOUNTS_MESSAGE;
        Optional<TelegramUser> telegramUser = telegramUserService.findById(getUserId(update));
        if (telegramUser.isPresent()) {
            List<Account> accounts = telegramUser.get().getAccounts();

            if (accounts.isEmpty()) {
                sendMessage = sendMessage + "\n Нет счетов";
            } else {
                int n = 1;
                for (Account account : accounts) {
                    sendMessage = sendMessage + "\n" + n + " - " + account.getTitle() + " - " + account.getDescription();
                    n++;
                }
            }
        } else {
            sendMessage = sendMessage + "\n Пользователь не найден";
        }
        sendBotMessageService.editMessageWithInlineKeyboard(getChatId(update), getMessageId(update), sendMessage, markupInline);
        updateUserLastActiveInDb(telegramUserService, update);
    }
}
