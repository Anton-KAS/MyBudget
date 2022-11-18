package ru.kas.myBudget.bots.telegram.callbacks;

import org.junit.jupiter.api.DisplayName;
import ru.kas.myBudget.bots.telegram.util.*;

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
