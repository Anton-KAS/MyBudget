package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.DisplayName;
import ru.kas.myBudget.bots.telegram.util.AbstractContainerTest;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for CommandContainer")
public class CommandContainerTest extends AbstractContainerTest {
    @Override
    protected void setContainer() {
        container = new CommandContainer(botMessageServiceMock, telegramUserServiceMock);
    }

    @Override
    protected void setNames() {
        commandNames = CommandNamesImpl.values();
    }

    @Override
    protected void setUnknownCommand() {
        unknownCommand = new UnknownCommand(botMessageServiceMock, telegramUserServiceMock, ExecuteMode.SEND,
                messageTextMock, keyboardMock);
    }
}
