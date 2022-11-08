package ru.kas.myBudget.bots.telegram.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.commands.CommandContainer;
import ru.kas.myBudget.bots.telegram.dialogs.DialogContainer;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.services.BotMessageServiceImpl;
import ru.kas.myBudget.services.*;

import java.util.Arrays;
import java.util.Map;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackType.*;
import static ru.kas.myBudget.bots.telegram.commands.CommandName.NO;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    public static String COMMAND_PREFIX = "/";

    @Value("${telegram.bot.username}")
    private String username;

    @Value("${telegram.bot.token}")
    private String token;

    private final static int CALLBACK_TYPE_INDEX = 0;
    public final static int CALLBACK_FROM_INDEX = 1;
    public final static String DIALOG_ID = "dialogId";
    private final static int CALLBACK_TO_INDEX = 2;

    private final CommandContainer commandContainer;
    private final CallbackContainer callbackContainer;
    private final DialogContainer dialogContainer;
    private final Map<Long, Map<String, String>> dialogsMap;

    @Autowired
    public TelegramBot(TelegramUserService telegramUserService, AccountService accountService,
                       CurrencyService currencyService, AccountTypeService accountTypeService,
                       BankService bankService) {
        this.commandContainer = new CommandContainer(
                new BotMessageServiceImpl(this), telegramUserService);
        this.callbackContainer = new CallbackContainer(
                new BotMessageServiceImpl(this), telegramUserService);
        this.dialogContainer = new DialogContainer(
                new BotMessageServiceImpl(this), telegramUserService, callbackContainer,
                accountTypeService, currencyService, bankService, accountService);
        this.dialogsMap = DialogsMap.getDialogsMap();
    }

    @Override
    public String getBotUsername() {
        return this.username;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.println(update); //TODO Add project Logger

            String message_text = update.getMessage().getText().trim();
            Long chatId = update.getMessage().getChatId();

            if (message_text.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message_text.split(" ")[0].toLowerCase();
                onCommandReceived(update, commandIdentifier);
            } else if (dialogsMap.containsKey(chatId)) {
                String dialogIdentifier = dialogsMap.get(chatId).get(DIALOG_ID);
                dialogContainer.retrieve(dialogIdentifier).execute(update);
            } else {
                commandContainer.retrieve(NO.getCommandName()).execute(update);
            }
        } else if (update.hasCallbackQuery()) {
            String[] callbackData = update.getCallbackQuery().getData().split("_");
            onCallbackReceived(update, callbackData);
        }
    }

    public void onCommandReceived(Update update, String commandIdentifier) {
        commandContainer.retrieve(commandIdentifier).execute(update);
    }

    public void onCallbackReceived(Update update, String[] callbackData) {
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();

        System.out.println("Call data: " + Arrays.toString(callbackData)); //TODO Add project Logger
        System.out.println("Message id: " + message_id); //TODO Add project Logger
        System.out.println("Chat id: " + chat_id); //TODO Add project Logger

        String callbackType = callbackData[CALLBACK_TYPE_INDEX];
        if (callbackType.equals(NORMAL.getId())) {
            callbackContainer.retrieve(callbackData[CALLBACK_TO_INDEX]).execute(update);
        } else if (callbackType.equals(DIALOG.getId())) {
            dialogContainer.retrieve(callbackData[CALLBACK_TO_INDEX]).execute(update);
        } else {
            callbackContainer.retrieve(NO.getCommandName()).execute(update);
        }
    }
}
