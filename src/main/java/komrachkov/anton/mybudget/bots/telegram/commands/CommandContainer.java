package komrachkov.anton.mybudget.bots.telegram.commands;

import com.google.common.collect.ImmutableMap;
import komrachkov.anton.mybudget.bots.telegram.callbacks.AccountsCallback;
import komrachkov.anton.mybudget.bots.telegram.util.CommandController;
import komrachkov.anton.mybudget.bots.telegram.util.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static komrachkov.anton.mybudget.bots.telegram.commands.CommandNamesImpl.*;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */

@Component
public class CommandContainer implements Container {
    private final ImmutableMap<String, CommandController> commandMap;
    private final CommandController unknownCommand;

    @Autowired
    public CommandContainer(UnknownCommand unknownCommand, StartCommand startCommand, HelpCommand helpCommand,
                            StopCommand stopCommand, CancelCommand cancelCommand, NoCommand noCommand,
                            StatCommand statCommand, MenuCommand menuCommand, AccountsCallback accountsCallback) {
        this.unknownCommand = unknownCommand;

        commandMap = ImmutableMap.<String, CommandController>builder()
                .put(START.getName(), startCommand)
                .put(HELP.getName(), helpCommand)
                .put(STOP.getName(), stopCommand)
                .put(CANCEL.getName(), cancelCommand)
                .put(NO.getName(), noCommand)
                .put(STAT.getName(), statCommand)
                .put(MENU.getName(), menuCommand)
                .put(ACCOUNTS.getName(), accountsCallback)
                .build();

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
