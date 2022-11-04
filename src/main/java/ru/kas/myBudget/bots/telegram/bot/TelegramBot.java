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
import ru.kas.myBudget.bots.telegram.services.SendBotMessageServiceImpl;
import ru.kas.myBudget.services.AccountService;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.HashMap;
import java.util.Map;

import static ru.kas.myBudget.bots.telegram.commands.CommandName.NO;

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
    public TelegramBot(TelegramUserService telegramUserService, AccountService accountService) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this), telegramUserService);
        this.callbackContainer = new CallbackContainer(new SendBotMessageServiceImpl(this), telegramUserService, accountService);
        this.dialogContainer = new DialogContainer(new SendBotMessageServiceImpl(this), telegramUserService);
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
            System.out.println(update);

            String message_text = update.getMessage().getText().trim();
            Long chatId = update.getMessage().getChatId();

            if (message_text.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message_text.split(" ")[0].toLowerCase();
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else if (dialogsMap.containsKey(chatId)) {
                String dialogIdentifier = dialogsMap.get(chatId).get("currentStep");
                dialogContainer.retrieveDialog(dialogIdentifier).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
            }
        } else if (update.hasCallbackQuery()) {
            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();

            System.out.println("Call data: " + call_data);
            System.out.println("Message id: " + message_id);
            System.out.println("Chat id: " + chat_id);

            String callbackIdentifier = call_data.split("_")[1].toLowerCase();
            callbackContainer.retrieveCommand(callbackIdentifier).execute(update);
        }

    }
}
