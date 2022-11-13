package ru.kas.myBudget.bots.telegram.callbacks;

import ru.kas.myBudget.bots.telegram.util.*;

public class CallbackContainerTest extends AbstractContainerTest {
    @Override
    protected void setContainer() {
        container = new CallbackContainer(botMessageServiceMock, telegramUserServiceMock);
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
