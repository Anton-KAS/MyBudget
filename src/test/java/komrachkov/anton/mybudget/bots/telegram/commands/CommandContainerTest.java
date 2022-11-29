package komrachkov.anton.mybudget.bots.telegram.commands;

import org.junit.jupiter.api.DisplayName;
import komrachkov.anton.mybudget.bots.telegram.util.AbstractContainerTest;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

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
