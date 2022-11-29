package komrachkov.anton.mybudget.bots.telegram.callbacks;

import com.google.common.collect.ImmutableMap;
import komrachkov.anton.mybudget.bots.telegram.keyboards.callback.AccountKeyboard;
import komrachkov.anton.mybudget.bots.telegram.keyboards.callback.AccountsKeyboard;
import komrachkov.anton.mybudget.bots.telegram.keyboards.commands.MenuKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.callback.AccountText;
import komrachkov.anton.mybudget.bots.telegram.texts.callback.CancelDialogText;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.MenuText;
import komrachkov.anton.mybudget.services.AccountService;
import komrachkov.anton.mybudget.services.TelegramUserService;
import komrachkov.anton.mybudget.bots.telegram.commands.MenuCommand;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.CancelDialogKeyboard;
import komrachkov.anton.mybudget.bots.telegram.keyboards.callback.NoKeyboard;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.texts.callback.AccountsText;
import komrachkov.anton.mybudget.bots.telegram.texts.callback.NoText;
import komrachkov.anton.mybudget.bots.telegram.util.Container;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.CommandController;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackNamesImpl.*;

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
