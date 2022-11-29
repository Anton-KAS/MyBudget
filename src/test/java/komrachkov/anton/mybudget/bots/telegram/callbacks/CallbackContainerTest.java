package komrachkov.anton.mybudget.bots.telegram.callbacks;

import komrachkov.anton.mybudget.bots.telegram.util.AbstractContainerTest;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import org.junit.jupiter.api.DisplayName;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for CallbackContainer")
public class CallbackContainerTest extends AbstractContainerTest {
    @Override
    protected void setContainer() {
        container = new CallbackContainer(botMessageServiceMock, telegramUserServiceMock, accountServiceMock);
    }

    @Override
    protected void setNames() {
        commandNames = CallbackNamesImpl.values();
    }

    @Override
    protected void setUnknownCommand() {
        unknownCommand = new UnknownCallback(botMessageServiceMock, telegramUserServiceMock, ExecuteMode.SEND,
                messageTextMock, keyboardMock);
    }
}
