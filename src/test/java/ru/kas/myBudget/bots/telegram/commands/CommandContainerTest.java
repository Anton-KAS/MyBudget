package ru.kas.myBudget.bots.telegram.commands;

import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackNamesImpl;
import ru.kas.myBudget.bots.telegram.callbacks.UnknownCallback;
import ru.kas.myBudget.bots.telegram.util.AbstractContainerTest;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;

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
