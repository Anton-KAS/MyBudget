package ru.kas.myBudget.bots.telegram.callbacks;

import com.google.common.collect.ImmutableMap;
import ru.kas.myBudget.bots.telegram.commands.MenuCommand;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.AccountsKeyboard;
import ru.kas.myBudget.bots.telegram.keyboards.CancelDialogKeyboard;
import ru.kas.myBudget.bots.telegram.keyboards.MenuKeyboard;
import ru.kas.myBudget.bots.telegram.keyboards.callback.AccountKeyboard;
import ru.kas.myBudget.bots.telegram.keyboards.callback.NoKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.AccountsText;
import ru.kas.myBudget.bots.telegram.texts.CancelDialogText;
import ru.kas.myBudget.bots.telegram.texts.MenuText;
import ru.kas.myBudget.bots.telegram.texts.callback.AccountText;
import ru.kas.myBudget.bots.telegram.texts.callback.NoText;
import ru.kas.myBudget.bots.telegram.util.CommandNames;
import ru.kas.myBudget.bots.telegram.util.Container;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.services.*;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackNamesImpl.*;

public class CallbackContainer implements Container {
    private final ImmutableMap<String, CommandController> callbackMap;
    private final CommandController unknownCommand;
    private final static ExecuteMode defaultExecuteMode = ExecuteMode.EDIT;

    public CallbackContainer(BotMessageService botMessageService, TelegramUserService telegramUserService,
                             AccountService accountService) {
        callbackMap = ImmutableMap.<String, CommandController>builder()
                .put(MENU.getName(),
                        new MenuCommand(botMessageService, telegramUserService, defaultExecuteMode,
                                new MenuText(telegramUserService), new MenuKeyboard()))
                .put(ACCOUNTS.getName(),
                        new AccountsCallback(botMessageService, telegramUserService, defaultExecuteMode,
                                new AccountsText(telegramUserService), new AccountsKeyboard()))
                .put(ACCOUNT.getName(),
                        new AccountCallback(botMessageService, telegramUserService, defaultExecuteMode,
                                new AccountText(), new AccountKeyboard(), accountService))
                .put(CLOSE.getName(),
                        new CloseCallback(botMessageService, telegramUserService, defaultExecuteMode,
                                null, null))
                .put(NO.getName(),
                        new NoCallback(botMessageService, telegramUserService, defaultExecuteMode,
                                new NoText(), new NoKeyboard()))
                .put(CANCEL_DIALOG.getName(),
                        new CancelDialogCallback(botMessageService, telegramUserService, defaultExecuteMode,
                                new CancelDialogText(), new CancelDialogKeyboard()))
                .build();

        unknownCommand = new UnknownCallback(botMessageService, telegramUserService, defaultExecuteMode,
                new NoText(), new NoKeyboard());
    }

    @Override
    public CommandController retrieve(String identifier) {
        return callbackMap.getOrDefault(identifier, unknownCommand);
    }

    @Override
    public boolean contains(String commandNames) {
        return callbackMap.containsKey(commandNames);
    }
}
