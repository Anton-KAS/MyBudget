package ru.kas.myBudget.bots.telegram.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.commands.CommandContainer;
import ru.kas.myBudget.bots.telegram.dialogs.DialogContainer;
import ru.kas.myBudget.bots.telegram.services.BotMessageServiceImpl;
import ru.kas.myBudget.bots.telegram.util.ResponseWaitingMap;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.*;

import java.util.Arrays;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackIndex.*;
import static ru.kas.myBudget.bots.telegram.callbacks.CallbackType.*;
import static ru.kas.myBudget.bots.telegram.commands.CommandNamesImpl.NO;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    public static String COMMAND_PREFIX = "/";

    @Value("${telegram.bot.username}")
    private String username;

    @Value("${telegram.bot.token}")
    private String token;

    private final CommandContainer commandContainer;
    private final CallbackContainer callbackContainer;
    private final DialogContainer dialogContainer;

    @Autowired
    public TelegramBot(TelegramUserService telegramUserService, AccountService accountService,
                       CurrencyService currencyService, AccountTypeService accountTypeService,
                       BankService bankService) {
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
        System.out.println(update); //TODO Add project Logger
        if (update.hasMessage() && update.getMessage().hasText()) {

            String messageText = UpdateParameter.getMessageText(update);
            long chatId = UpdateParameter.getChatId(update);

            if (ResponseWaitingMap.contains(chatId)) {
                String identifier = ResponseWaitingMap.get(chatId).getName();

                System.out.println("WAITING BY: " + identifier); //TODO Add project Logger

                if (commandContainer.contains(identifier)) {
                    System.out.println("COMMAND CONTAINER"); //TODO Add project Logger
                    commandContainer.retrieve(identifier).execute(update);
                } else if (callbackContainer.contains(identifier)) {
                    System.out.println("CALLBACK CONTAINER"); //TODO Add project Logger
                    callbackContainer.retrieve(identifier).execute(update);
                } else if (dialogContainer.contains(identifier)) {
                    System.out.println("DIALOG CONTAINER"); //TODO Add project Logger
                    dialogContainer.retrieve(identifier).execute(update);
                } else {
                    System.out.println("NO CONTAINER"); //TODO Add project Logger
                    commandContainer.retrieve(NO.getName()).execute(update);
                }

            } else if (messageText.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = messageText.split(" ")[0].toLowerCase();

                System.out.println("COMMAND ID: " + commandIdentifier); //TODO Add project Logger

                onCommandReceived(update, commandIdentifier);

            } else {
                commandContainer.retrieve(NO.getName()).execute(update);
            }

        } else if (update.hasCallbackQuery()) {
            String[] callbackData = UpdateParameter.getCallbackData(update);
            onCallbackReceived(update, callbackData);
        }
    }

    public void onCommandReceived(Update update, String commandIdentifier) {
        commandContainer.retrieve(commandIdentifier).execute(update);
    }

    public void onCallbackReceived(Update update, String[] callbackData) {
        long messageId = UpdateParameter.getMessageId(update);
        long chatId = UpdateParameter.getChatId(update);

        System.out.println("Call data: " + Arrays.toString(callbackData)); //TODO Add project Logger
        System.out.println("Message id: " + messageId); //TODO Add project Logger
        System.out.println("Chat id: " + chatId); //TODO Add project Logger

        String callbackType = callbackData[TYPE.getIndex()];
        if (callbackType.equals(NORMAL.getId()))
            callbackContainer.retrieve(callbackData[TO.getIndex()]).execute(update);
        else if (callbackType.equals(DIALOG.getId()))
            dialogContainer.retrieve(callbackData[TO.getIndex()]).execute(update);
        else callbackContainer.retrieve(NO.getName()).execute(update);
    }
}
