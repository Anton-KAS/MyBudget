package komrachkov.anton.mybudget.bots.telegram.bot;

import komrachkov.anton.mybudget.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackContainer;
import komrachkov.anton.mybudget.bots.telegram.commands.CommandContainer;
import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogContainer;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageServiceImpl;
import komrachkov.anton.mybudget.bots.telegram.util.ResponseWaitingMap;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;

import java.util.Arrays;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex.*;
import static komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackType.*;
import static komrachkov.anton.mybudget.bots.telegram.commands.CommandNamesImpl.NO;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */

@Component
public class TelegramBot extends TelegramLongPollingBot {
    public static String COMMAND_PREFIX = "/";

    @Value("${telegram.bot.username}")
    private String username;

    @Value("${telegram.bot.token}")
    private String token;

    private final TelegramUserService telegramUserService;

    private final CommandContainer commandContainer;
    private final CallbackContainer callbackContainer;
    private final DialogContainer dialogContainer;

    @Autowired
    public TelegramBot(TelegramUserService telegramUserService, AccountService accountService,
                       CurrencyService currencyService, AccountTypeService accountTypeService,
                       BankService bankService) {
        this.telegramUserService = telegramUserService;
        this.commandContainer = new CommandContainer(
                new BotMessageServiceImpl(this), telegramUserService);
        this.callbackContainer = new CallbackContainer(
                new BotMessageServiceImpl(this), telegramUserService, accountService);
        this.dialogContainer = new DialogContainer(
                new BotMessageServiceImpl(this), telegramUserService, callbackContainer,
                accountTypeService, currencyService, bankService, accountService);
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
        System.out.println(update); //TODO: Add project Logger
        telegramUserService.checkUser(telegramUserService, update);
        if (update.hasMessage() && update.getMessage().hasText()) {
            onTextMessageReceived(update);
            return;
        }
        if (update.hasCallbackQuery()) {
            onCallbackReceived(update);
            return;
        }
        commandContainer.retrieve(NO.getName()).execute(update);
    }

    private void onTextMessageReceived(Update update) {
        long chatId = UpdateParameter.getChatId(update);
        if (ResponseWaitingMap.contains(chatId)) {
            String identifier = ResponseWaitingMap.get(chatId).getName();
            onWaitingReceived(update, identifier);
            return;
        }

        String messageText = UpdateParameter.getMessageText(update);
        if (messageText.startsWith(COMMAND_PREFIX)) {
            String commandIdentifier = messageText.split(" ")[0].toLowerCase();
            System.out.println("COMMAND ID: " + commandIdentifier); //TODO: Add project Logger
            commandContainer.retrieve(commandIdentifier).execute(update);
            return;
        }
        commandContainer.retrieve(NO.getName()).execute(update);
    }

    private void onWaitingReceived(Update update, String identifier) {
        System.out.println("WAITING BY: " + identifier); //TODO: Add project Logger
        if (commandContainer.contains(identifier)) {
            System.out.println("COMMAND CONTAINER"); //TODO: Add project Logger
            commandContainer.retrieve(identifier).execute(update);
            return;
        }
        if (callbackContainer.contains(identifier)) {
            System.out.println("CALLBACK CONTAINER"); //TODO: Add project Logger
            callbackContainer.retrieve(identifier).execute(update);
            return;
        }
        if (dialogContainer.contains(identifier)) {
            System.out.println("DIALOG CONTAINER"); //TODO: Add project Logger
            dialogContainer.retrieve(identifier).execute(update);
            return;
        }
        System.out.println("NO CONTAINER"); //TODO: Add project Logger
        commandContainer.retrieve(NO.getName()).execute(update);
    }

    private void onCallbackReceived(Update update) {
        long messageId = UpdateParameter.getMessageId(update);
        long chatId = UpdateParameter.getChatId(update);

        System.out.println("Message id: " + messageId); //TODO: Add project Logger
        System.out.println("Chat id: " + chatId); //TODO: Add project Logger

        String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);
        System.out.println("Call data: " + Arrays.toString(callbackData)); //TODO: Add project Logger
        if (callbackData == null || callbackData.length <= TO.ordinal()) {
            callbackContainer.retrieve(NO.getName()).execute(update);
            return;
        }
        String callbackType = callbackData[TYPE.ordinal()];
        if (callbackType.equals(NORMAL.getId())) {
            callbackContainer.retrieve(callbackData[TO.ordinal()]).execute(update);
            return;
        }
        if (callbackType.equals(DIALOG.getId()) && dialogContainer.contains(callbackData[TO.ordinal()])) {
            dialogContainer.retrieve(callbackData[TO.ordinal()]).execute(update);
            return;
        }
        callbackContainer.retrieve(NO.getName()).execute(update);
    }
}
