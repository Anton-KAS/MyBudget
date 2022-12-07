package komrachkov.anton.mybudget.bots.telegram.bot;

import komrachkov.anton.mybudget.bots.telegram.services.BotMessageServiceImpl;
import komrachkov.anton.mybudget.bots.telegram.util.Logger;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.services.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackContainer;
import komrachkov.anton.mybudget.bots.telegram.commands.CommandContainer;
import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogContainer;
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
@Log4j2
public class TelegramBot extends TelegramLongPollingBot {
    public static String COMMAND_PREFIX = "/";

    @Value("${telegram.bot.username}")
    private String username;

    @Value("${telegram.bot.token}")
    private String token;

    private long chatId;
    private String logStartText;

    private final TelegramUserService telegramUserService;
    private final BotMessageService botMessageService;

    private final CommandContainer commandContainer;
    private final CallbackContainer callbackContainer;
    private final DialogContainer dialogContainer;

    @Autowired
    public TelegramBot(TelegramUserService telegramUserService, CommandContainer commandContainer,
                       CallbackContainer callbackContainer, DialogContainer dialogContainer) {
        this.telegramUserService = telegramUserService;
        this.commandContainer = commandContainer;
        this.callbackContainer = callbackContainer;
        this.dialogContainer = dialogContainer;

        this.botMessageService = new BotMessageServiceImpl(this, telegramUserService);
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
        chatId = UpdateParameter.getChatId(update);
        this.logStartText = Logger.getLogStartText(update);
        log.info(logStartText + "Received Telegram Update " + UpdateParameter.getMessageId(update));
        log.debug(logStartText + update);

        telegramUserService.checkUser(telegramUserService, update);
        ToDoList toDoList;
        if (update.hasCallbackQuery() && UpdateParameter.getCallbackData(update).isPresent()
                && UpdateParameter.getCallbackData(update).get()[0].equals(NOTHING.getName())) {
            log.info(logStartText + "Do nothing. Callback: " + UpdateParameter.getCallbackData(update));
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
            log.info(logStartText + "ToDo for bot message service: " + toDo.executeMode());
            botMessageService.executeAndUpdateUser(toDo.update(), toDo.executeMode(), toDo.text(), toDo.inlineKeyboardMarkup());
        }
    }

    private ToDoList onTextMessageReceived(Update update) {
        if (ResponseWaitingMap.contains(chatId)) {
            String identifier = ResponseWaitingMap.get(chatId).getName();
            return onWaitingReceived(update, identifier);
        }

        String messageText = UpdateParameter.getMessageText(update);
        if (messageText.startsWith(COMMAND_PREFIX)) {
            String commandIdentifier = messageText.split(" ")[0].toLowerCase();
            log.info(logStartText + "Command received: " + commandIdentifier);
            return commandContainer.retrieve(commandIdentifier).execute(update);
        }
        log.info(logStartText + "No command received: " + UpdateParameter.getMessageText(update));
        return commandContainer.retrieve(NO.getName()).execute(update);
    }

    private ToDoList onWaitingReceived(Update update, String identifier) {
        log.info(logStartText + "Waiting response: " + identifier);
        if (commandContainer.contains(identifier)) {
            log.info(logStartText + "Command: " + identifier);
            return commandContainer.retrieve(identifier).execute(update);
        }

        if (callbackContainer.contains(identifier)) {
            log.info(logStartText + "Callback: " + identifier);
            return callbackContainer.retrieve(identifier).execute(update);
        }

        if (dialogContainer.contains(identifier)) {
            log.info(logStartText + "Dialog: " + identifier);
            return dialogContainer.retrieve(identifier).execute(update);
        }

        log.warn(logStartText + "UNKNOWN: " + identifier);
        return commandContainer.retrieve(NO.getName()).execute(update);

    }

    private ToDoList onCallbackReceived(Update update) {
        String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);
        log.info(logStartText + "Callback received: " + Arrays.toString(callbackData));
        if (callbackData == null || callbackData.length <= TO.ordinal()) {
            log.warn(logStartText + "UNKNOWN: " + Arrays.toString(callbackData));
            return callbackContainer.retrieve(NO.getName()).execute(update);
        }

        String callbackType = callbackData[TYPE.ordinal()];
        if (callbackType.equals(NORMAL.getId())) {
            log.info(logStartText + "Callback from: " + callbackData[FROM.ordinal()] + ", to: " + callbackData[TO.ordinal()]);
            return callbackContainer.retrieve(callbackData[TO.ordinal()]).execute(update);
        }

        if (callbackType.equals(DIALOG.getId()) && dialogContainer.contains(callbackData[TO.ordinal()])) {
            log.info(logStartText + "Dialog from: " + callbackData[FROM.ordinal()] + ", to: " + callbackData[TO.ordinal()]);
            return dialogContainer.retrieve(callbackData[TO.ordinal()]).execute(update);
        }

        log.warn(logStartText + "UNKNOWN: " + Arrays.toString(callbackData));
        return callbackContainer.retrieve(NO.getName()).execute(update);
    }
}
