package komrachkov.anton.mybudget.bots.telegram.commands;

import com.google.common.collect.ImmutableMap;
import komrachkov.anton.mybudget.bots.telegram.commands.util.CommandControllerTest;
import komrachkov.anton.mybudget.bots.telegram.commands.util.ContainerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static komrachkov.anton.mybudget.bots.telegram.commands.CommandNamesImpl.*;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */

@Component
public class CommandContainer implements ContainerTest {
    private final ImmutableMap<String, CommandControllerTest> commandMap;
    private final CommandControllerTest unknownCommand;

    @Autowired
    public CommandContainer(UnknownCommand unknownCommand, StartCommand startCommand, HelpCommand helpCommand,
                            StopCommand stopCommand, CancelCommand cancelCommand, NoCommand noCommand,
                            StatCommand statCommand, MenuCommand menuCommand) {
        this.unknownCommand = unknownCommand;

        commandMap = ImmutableMap.<String, CommandControllerTest>builder()
                .put(START.getName(), startCommand)
                .put(HELP.getName(), helpCommand)
                .put(STOP.getName(), stopCommand)
                .put(CANCEL.getName(), cancelCommand)
                .put(NO.getName(), noCommand)
                .put(STAT.getName(), statCommand)
                .put(MENU.getName(), menuCommand)
//                .put(ACCOUNTS.getName(), new AccountsCallback(sendBotMessageService, telegramUserService,
//                        defaultExecuteMode, new AccountsText(telegramUserService), new AccountsKeyboard()))
                .build();

    }

    @Override
    public CommandControllerTest retrieve(String identifier) {
        return commandMap.getOrDefault(identifier, unknownCommand);
    }

    @Override
    public boolean contains(String commandNames) {
        return commandMap.containsKey(commandNames);
    }
}
