package ru.kas.myBudget.bots.telegram.callbacks;

import com.google.common.collect.ImmutableMap;
import ru.kas.myBudget.bots.telegram.commands.MenuCommand;
import ru.kas.myBudget.bots.telegram.keyboards.callback.AccountsKeyboard;
import ru.kas.myBudget.bots.telegram.keyboards.CancelDialogKeyboard;
import ru.kas.myBudget.bots.telegram.keyboards.commands.MenuKeyboard;
import ru.kas.myBudget.bots.telegram.keyboards.callback.AccountKeyboard;
import ru.kas.myBudget.bots.telegram.keyboards.callback.NoKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.callback.AccountsText;
import ru.kas.myBudget.bots.telegram.texts.callback.CancelDialogText;
import ru.kas.myBudget.bots.telegram.texts.commands.MenuText;
import ru.kas.myBudget.bots.telegram.texts.callback.AccountText;
import ru.kas.myBudget.bots.telegram.texts.callback.NoText;
import ru.kas.myBudget.bots.telegram.util.Container;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.services.*;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackNamesImpl.*;

/**
 * Контейнер для всех классов по списку {@link CallbackNamesImpl}
 *
 * @author Anton Komrachkov
 * @since 0.2
 */

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
                                new CancelDialogText(), new CancelDialogKeyboard(), this))
                .build();

        unknownCommand = new UnknownCallback(botMessageService, telegramUserService, defaultExecuteMode,
                new NoText(), new NoKeyboard());
    }

    /**
     * Возращает класс для обработки CallbackData команды
     *
     * @param identifier String ID по списку {@link CallbackNamesImpl}
     * @return CommandController
     */
    @Override
    public CommandController retrieve(String identifier) {
        return callbackMap.getOrDefault(identifier, unknownCommand);
    }

    /**
     * Проверяет наличие класса в контейнере
     *
     * @param identifier String ID по списку {@link CallbackNamesImpl}
     * @return CommandController
     */
    @Override
    public boolean contains(String identifier) {
        return callbackMap.containsKey(identifier);
    }
}
