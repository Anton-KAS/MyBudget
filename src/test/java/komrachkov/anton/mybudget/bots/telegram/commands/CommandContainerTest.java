package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.callbacks.AccountsCallback;
import org.junit.jupiter.api.DisplayName;
import komrachkov.anton.mybudget.bots.telegram.util.AbstractContainerTest;
import org.mockito.Mockito;

/**
 * Unit-tests for {@link CommandContainer}
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for CommandContainer")
public class CommandContainerTest extends AbstractContainerTest {
    private final static UnknownCommand unknownCommandMock = Mockito.mock(UnknownCommand.class);
    private final static StartCommand startCommandMock = Mockito.mock(StartCommand.class);
    private final static HelpCommand helpCommandMock = Mockito.mock(HelpCommand.class);
    private final static StopCommand stopCommandMock = Mockito.mock(StopCommand.class);
    private final static CancelCommand cancelCommandMock = Mockito.mock(CancelCommand.class);
    private final static NoCommand noCommandMock = Mockito.mock(NoCommand.class);
    private final static StatCommand statCommandMock = Mockito.mock(StatCommand.class);
    private final static MenuCommand menuCommandMock = Mockito.mock(MenuCommand.class);
    private final static AccountsCallback accountsCallbackMock = Mockito.mock(AccountsCallback.class);

    @Override
    protected void setContainer() {
        container = new CommandContainer(unknownCommandMock, startCommandMock, helpCommandMock, stopCommandMock,
                cancelCommandMock, noCommandMock, statCommandMock, menuCommandMock, accountsCallbackMock);
    }

    @Override
    protected void setNames() {
        commandNames = CommandNamesImpl.values();
    }

    @Override
    protected void setUnknownCommand() {
        unknownCommand = Mockito.mock(UnknownCommand.class);
    }
}
