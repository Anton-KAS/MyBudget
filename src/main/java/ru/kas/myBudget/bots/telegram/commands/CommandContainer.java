package ru.kas.myBudget.bots.telegram.commands;

import com.google.common.collect.ImmutableMap;
import ru.kas.myBudget.bots.telegram.callbacks.AccountsCallback;
import ru.kas.myBudget.bots.telegram.keyboards.MenuKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MenuText;
import ru.kas.myBudget.bots.telegram.util.Container;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.services.TelegramUserService;

import static ru.kas.myBudget.bots.telegram.commands.CommandName.*;

public class CommandContainer implements Container {
    private final static ExecuteMode defaultExecuteMode = ExecuteMode.SEND;
    private final ImmutableMap<String, CommandController> commandMap;
    private final CommandController unknownCommand;

    public CommandContainer(BotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        commandMap = ImmutableMap.<String, CommandController>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService, telegramUserService,
                        defaultExecuteMode))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService, telegramUserService))
                .put(STAT.getCommandName(), new StatCommand(sendBotMessageService, telegramUserService))
                .put(MENU.getCommandName(), new MenuCommand(sendBotMessageService, telegramUserService,
                        defaultExecuteMode, new MenuKeyboard(), new MenuText(telegramUserService)))
                .put(ACCOUNTS.getCommandName(), new AccountsCallback(sendBotMessageService, telegramUserService,
                        defaultExecuteMode))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService, telegramUserService);
    }

    @Override
    public CommandController retrieve(String identifier) {
        return commandMap.getOrDefault(identifier, unknownCommand);
    }
}
