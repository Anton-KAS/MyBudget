package ru.kas.myBudget.bots.telegram.commands;

import com.google.common.collect.ImmutableMap;
import ru.kas.myBudget.bots.telegram.callbacks.AccountsCallback;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.Container;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateExtraction;
import ru.kas.myBudget.services.TelegramUserService;

import static ru.kas.myBudget.bots.telegram.commands.CommandName.*;

public class CommandContainer implements Container {

    private final static ExecuteMode defaultExecuteMode = ExecuteMode.SEND;
    private final ImmutableMap<String, UpdateExtraction> commandMap;
    private final Command unknownCommand;

    public CommandContainer(BotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        commandMap = ImmutableMap.<String, UpdateExtraction>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService, telegramUserService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put(STAT.getCommandName(), new StatCommand(sendBotMessageService, telegramUserService))
                .put(MENU.getCommandName(), new MenuCommand(defaultExecuteMode, sendBotMessageService, telegramUserService))
                .put(ACCOUNTS.getCommandName(), new AccountsCallback(defaultExecuteMode, sendBotMessageService,
                        telegramUserService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    @Override
    public UpdateExtraction retrieve(String identifier) {
        return commandMap.getOrDefault(identifier, unknownCommand);
    }
}
