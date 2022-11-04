package ru.kas.myBudget.bots.telegram.callbacks;

import com.google.common.collect.ImmutableMap;
import ru.kas.myBudget.bots.telegram.services.SendBotMessageService;
import ru.kas.myBudget.services.AccountService;
import ru.kas.myBudget.services.TelegramUserService;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackName.*;

public class CallbackContainer {

    private final ImmutableMap<String, Callback> callbackMap;
    private final Callback unknownCommand;

    public CallbackContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, AccountService accountService) {
        callbackMap = ImmutableMap.<String, Callback>builder()
                .put(ACCOUNTS.getCallbackName(), new AccountsCallback(sendBotMessageService, telegramUserService, accountService))
                .put(MENU.getCallbackName(), new MenuCallback(sendBotMessageService, telegramUserService))
                .put(CLOSE.getCallbackName(), new CloseCallback(sendBotMessageService, telegramUserService))
                .put(NO.getCallbackName(), new NoCallback(sendBotMessageService))
                .build();

        unknownCommand = new UnknownCallback(sendBotMessageService);
    }

    public Callback retrieveCommand(String commandIdentifier) {
        return callbackMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
