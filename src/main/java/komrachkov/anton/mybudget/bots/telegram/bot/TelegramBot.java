package komrachkov.anton.mybudget.bots.telegram.bot;

import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
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

import static komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackNamesImpl.NOTHING;
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
    private final BotMessageService botMessageService;

    private final CommandContainer commandContainer;
    private final CallbackContainer callbackContainer;
    private final DialogContainer dialogContainer;

    @Autowired
    public TelegramBot(TelegramUserService telegramUserService, CommandContainer commandContainer,
                       CallbackContainer callbackContainer, DialogContainer dialogContainer) {
        this.telegramUserService = telegramUserService;
        this.botMessageService = new BotMessageServiceImpl(this);
        this.commandContainer = commandContainer;
        this.callbackContainer = callbackContainer;
        this.dialogContainer = dialogContainer;
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

        ToDoList toDoList;
        if (update.hasCallbackQuery() && UpdateParameter.getCallbackData(update).isPresent()
                && UpdateParameter.getCallbackData(update).get()[0].equals(NOTHING.getName())) {
            return;
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            toDoList = onTextMessageReceived(update);
        } else if (update.hasCallbackQuery()) {
            toDoList = onCallbackReceived(update);
        } else {
            toDoList = commandContainer.retrieve(NO.getName()).execute(update);
        }

        if (toDoList == null) return;
        while (toDoList.hasToDo()) {
            ToDoList.ToDo toDo = toDoList.pollToDo();
            botMessageService.executeAndUpdateUser(telegramUserService, toDo.update(), toDo.executeMode(),
                    toDo.text(), toDo.inlineKeyboardMarkup());
        }
    }

    private ToDoList onTextMessageReceived(Update update) {
        long chatId = UpdateParameter.getChatId(update);
        if (ResponseWaitingMap.contains(chatId)) {
            String identifier = ResponseWaitingMap.get(chatId).getName();
            return onWaitingReceived(update, identifier);
        }

        String messageText = UpdateParameter.getMessageText(update);
        if (messageText.startsWith(COMMAND_PREFIX)) {
            String commandIdentifier = messageText.split(" ")[0].toLowerCase();
            System.out.println("COMMAND ID: " + commandIdentifier); //TODO: Add project Logger
            return commandContainer.retrieve(commandIdentifier).execute(update);
        }

        return commandContainer.retrieve(NO.getName()).execute(update);
    }

    private ToDoList onWaitingReceived(Update update, String identifier) {
        System.out.println("WAITING BY: " + identifier); //TODO: Add project Logger
        if (commandContainer.contains(identifier)) {
            System.out.println("COMMAND CONTAINER"); //TODO: Add project Logger
            return commandContainer.retrieve(identifier).execute(update);
        }

        if (callbackContainer.contains(identifier)) {
            System.out.println("CALLBACK CONTAINER"); //TODO: Add project Logger
            return callbackContainer.retrieve(identifier).execute(update);
        }

        if (dialogContainer.contains(identifier)) {
            System.out.println("DIALOG CONTAINER"); //TODO: Add project Logger
            return dialogContainer.retrieve(identifier).execute(update);
        }

        System.out.println("NO CONTAINER"); //TODO: Add project Logger
        return commandContainer.retrieve(NO.getName()).execute(update);

    }

    private ToDoList onCallbackReceived(Update update) {
        long messageId = UpdateParameter.getMessageId(update);
        long chatId = UpdateParameter.getChatId(update);
        System.out.println("Message id: " + messageId); //TODO: Add project Logger
        System.out.println("Chat id: " + chatId); //TODO: Add project Logger

        String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);
        System.out.println("Call data: " + Arrays.toString(callbackData)); //TODO: Add project Logger
        if (callbackData == null || callbackData.length <= TO.ordinal()) {
            return callbackContainer.retrieve(NO.getName()).execute(update);
        }

        String callbackType = callbackData[TYPE.ordinal()];
        if (callbackType.equals(NORMAL.getId())) {
            return callbackContainer.retrieve(callbackData[TO.ordinal()]).execute(update);
        }

        if (callbackType.equals(DIALOG.getId()) && dialogContainer.contains(callbackData[TO.ordinal()])) {
            return dialogContainer.retrieve(callbackData[TO.ordinal()]).execute(update);
        }

        return callbackContainer.retrieve(NO.getName()).execute(update);
    }
}
