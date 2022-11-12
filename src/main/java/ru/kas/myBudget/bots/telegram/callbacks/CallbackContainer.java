package ru.kas.myBudget.bots.telegram.callbacks;

import com.google.common.collect.ImmutableMap;
import ru.kas.myBudget.bots.telegram.commands.MenuCommand;
import ru.kas.myBudget.bots.telegram.keyboards.MenuKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MenuText;
import ru.kas.myBudget.bots.telegram.util.Container;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.services.*;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackName.*;

public class CallbackContainer implements Container {
    private final ImmutableMap<String, CommandController> callbackMap;
    private final CommandController unknownCommand;
    private final static ExecuteMode defaultExecuteMode = ExecuteMode.EDIT;

    public CallbackContainer(BotMessageService botMessageService, TelegramUserService telegramUserService) {
        callbackMap = ImmutableMap.<String, CommandController>builder()
                .put(ACCOUNTS.getCallbackName(),
                        new AccountsCallback(botMessageService, telegramUserService, defaultExecuteMode))
                .put(MENU.getCallbackName(),
                        new MenuCommand(botMessageService, telegramUserService, defaultExecuteMode,
                                new MenuKeyboard(), new MenuText(telegramUserService)))
                .put(CLOSE.getCallbackName(),
                        new CloseCallback(botMessageService, telegramUserService))
                .put(NO.getCallbackName(),
                        new NoCallback(botMessageService, telegramUserService, defaultExecuteMode))
                .put(CANCEL_DIALOG.getCallbackName(),
                        new CancelCallbackDialog(botMessageService, telegramUserService))
                .build();

        unknownCommand = new UnknownCallback(botMessageService, telegramUserService, defaultExecuteMode);
    }

    @Override
    public CommandController retrieve(String identifier) {
        return callbackMap.getOrDefault(identifier, unknownCommand);
    }
}
