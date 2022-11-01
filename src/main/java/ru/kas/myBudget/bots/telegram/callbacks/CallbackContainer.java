package ru.kas.myBudget.bots.telegram.callbacks;

import com.google.common.collect.ImmutableMap;
import ru.kas.myBudget.bots.telegram.services.SendBotMessageService;
import ru.kas.myBudget.services.TelegramUserService;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackName.*;

public class CallbackContainer {

    private final ImmutableMap<String, Callback> callbackMap;
    private final Callback unknownCommand;

    public CallbackContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        callbackMap = ImmutableMap.<String, Callback>builder()
                .put(ACCOUNTS.getCallbackName(), new AccountsCallback(sendBotMessageService, telegramUserService))
                .put(MENU.getCallbackName(), new MenuCallback(sendBotMessageService, telegramUserService))
                .put(NO.getCallbackName(), new NoCallback(sendBotMessageService))
                .build();

        unknownCommand = new UnknownCallback(sendBotMessageService);
    }

    public Callback retrieveCommand(String commandIdentifier) {
        return callbackMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
