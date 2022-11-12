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
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.*;

import java.util.Arrays;
import java.util.Map;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackIndex.*;
import static ru.kas.myBudget.bots.telegram.callbacks.CallbackType.*;
import static ru.kas.myBudget.bots.telegram.commands.CommandName.NO;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.DIALOG_ID;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogPattern.EDIT_NUM;

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
        System.out.println(update); //TODO Add project Logger
        if (update.hasMessage() && update.getMessage().hasText()) {

            String messageText = UpdateParameter.getMessageText(update);
            long chatId = UpdateParameter.getChatId(update);

            if (messageText != null && messageText.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = messageText.split(" ")[0].toLowerCase();

                System.out.println("COMMAND ID: " + commandIdentifier); //TODO Add project Logger

                if (commandIdentifier.matches(EDIT_NUM.getRegex()) && dialogsMap.containsKey(chatId)) {
                    String dialogIdentifier = dialogsMap.get(chatId).get(DIALOG_ID.getId());
                    dialogContainer.retrieve(dialogIdentifier).execute(update);
                } else {
                    onCommandReceived(update, commandIdentifier);
                }

            } else if (dialogsMap.containsKey(chatId)) {
                String dialogIdentifier = dialogsMap.get(chatId).get(DIALOG_ID.getId());
                dialogContainer.retrieve(dialogIdentifier).execute(update);
            } else {
                commandContainer.retrieve(NO.getCommandName()).execute(update);
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
        else callbackContainer.retrieve(NO.getCommandName()).execute(update);
    }
}
