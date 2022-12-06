package komrachkov.anton.mybudget.bots.telegram.callbacks;

import komrachkov.anton.mybudget.bots.telegram.commands.MenuCommand;
import komrachkov.anton.mybudget.bots.telegram.util.AbstractContainerTest;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;

/**
 * Unit-tests for {@link CallbackContainer}
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for CallbackContainer")
public class CallbackContainerTest extends AbstractContainerTest {
    private final static MenuCommand menuCommandMock = Mockito.mock(MenuCommand.class);
    private final static AccountsCallback accountsCallbackMock = Mockito.mock(AccountsCallback.class);
    private final static UnknownCallback unknownCallbackMock = Mockito.mock(UnknownCallback.class);
    private final static AccountCallback accountCallbackMock = Mockito.mock(AccountCallback.class);
    private final static CloseCallback closeCallbackMock = Mockito.mock(CloseCallback.class);
    private final static NothingCallback nothingCallback = Mockito.mock(NothingCallback.class);
    private final static NoCallback noCallbackMock = Mockito.mock(NoCallback.class);

    @Override
    protected void setContainer() {
        container = new CallbackContainer(menuCommandMock, accountsCallbackMock, unknownCallbackMock,
                accountCallbackMock, closeCallbackMock, nothingCallback, noCallbackMock);
    }

    @Override
    protected void setNames() {
        commandNames = CallbackNamesImpl.values();
    }

    @Override
    protected void setUnknownCommand() {
        unknownCommand = Mockito.mock(UnknownCallback.class);
    }
}
