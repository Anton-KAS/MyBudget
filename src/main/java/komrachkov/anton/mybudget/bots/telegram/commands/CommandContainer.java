package komrachkov.anton.mybudget.bots.telegram.commands;

import com.google.common.collect.ImmutableMap;
import komrachkov.anton.mybudget.bots.telegram.callbacks.AccountsCallback;
import komrachkov.anton.mybudget.bots.telegram.keyboards.callback.AccountsKeyboard;
import komrachkov.anton.mybudget.bots.telegram.keyboards.commands.*;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.*;
import komrachkov.anton.mybudget.bots.telegram.util.Container;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.services.TelegramUserService;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.texts.callback.AccountsText;
import komrachkov.anton.mybudget.bots.telegram.util.CommandController;

import static komrachkov.anton.mybudget.bots.telegram.commands.CommandNamesImpl.*;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */

public class CommandContainer implements Container {
    private final static ExecuteMode defaultExecuteMode = ExecuteMode.SEND;
    private final ImmutableMap<String, CommandController> commandMap;
    private final CommandController unknownCommand;

    public CommandContainer(BotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        commandMap = ImmutableMap.<String, CommandController>builder()
                .put(START.getName(), new StartCommand(sendBotMessageService, telegramUserService,
                        defaultExecuteMode, new StartText(), new StartKeyboard()))
                .put(HELP.getName(), new HelpCommand(sendBotMessageService, telegramUserService,
                        defaultExecuteMode, new HelpText(), new HelpKeyboard()))
                .put(STOP.getName(), new StopCommand(sendBotMessageService, telegramUserService,
                        defaultExecuteMode, new StopText(), new StopKeyboard()))
                .put(CANCEL.getName(), new CancelCommand(sendBotMessageService, telegramUserService,
                        defaultExecuteMode, new CancelText(), new CancelKeyboard()))
                .put(NO.getName(), new NoCommand(sendBotMessageService, telegramUserService,
                        defaultExecuteMode, new NoText(), new NoKeyboard()))
                .put(STAT.getName(), new StatCommand(sendBotMessageService, telegramUserService,
                        defaultExecuteMode, new StatText(), new StatKeyboard()))
                .put(MENU.getName(), new MenuCommand(sendBotMessageService, telegramUserService,
                        defaultExecuteMode, new MenuText(telegramUserService), new MenuKeyboard()))
                .put(ACCOUNTS.getName(), new AccountsCallback(sendBotMessageService, telegramUserService,
                        defaultExecuteMode, new AccountsText(telegramUserService), new AccountsKeyboard()))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService, telegramUserService,
                defaultExecuteMode, new UnknownText(), new UnknownKeyboard());
    }

    @Override
    public CommandController retrieve(String identifier) {
        return commandMap.getOrDefault(identifier, unknownCommand);
    }

    @Override
    public boolean contains(String commandNames) {
        return commandMap.containsKey(commandNames);
    }
}
