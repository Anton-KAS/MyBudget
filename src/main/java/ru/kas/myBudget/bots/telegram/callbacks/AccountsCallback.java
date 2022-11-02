package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.kas.myBudget.bots.telegram.services.SendBotMessageService;
import ru.kas.myBudget.models.Account;
import ru.kas.myBudget.services.AccountService;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.ArrayList;
import java.util.List;

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

        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("<- Назад");
        inlineKeyboardButton2.setCallbackData("accounts_menu");
        rowInline.add(inlineKeyboardButton2);

        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);

        sendMessage = ACCOUNTS_MESSAGE;

        System.out.println("USER ID: " + getUserId(update));
        System.out.println(update);
        List<Account> accounts = accountService.findAllByTelegramUserId(getUserId(update));
        if (accounts.isEmpty()) {
            sendMessage += "\nНет счетов";
        } else {
            for (Account account : accounts) {
                sendMessage += "\n" + account.getTitle() + " - " + account.getDescription();
            }
        }

//        telegramUserService.findById(getUserId(update)).ifPresentOrElse(
//                user -> {
//                    List<Account> accounts = user.getAccounts();
//                    if (accounts.isEmpty()) {
//                        sendMessage += "\nНет счетов";
//                    } else {
//                        for (Account account: accounts) {
//                            sendMessage += "\n" + account.getTitle() + " - " + account.getDescription();
//                        }
//                    }
//                },
//                () -> {
//                    sendMessage += "\nПользователь не найден";
//                }
//        );
            sendBotMessageService.editMessageWithInlineKeyboard(getChatId(update), getMessageId(update), sendMessage, markupInline);
        }
    }
