package komrachkov.anton.mybudget.bots.telegram.callbacks;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import komrachkov.anton.mybudget.bots.telegram.commands.MenuCommand;
import komrachkov.anton.mybudget.bots.telegram.util.AbstractCommandControllerTest;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackNamesImpl.ACCOUNTS;
import static komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackNamesImpl.MENU;

public abstract class AbstractCallbackTest extends AbstractCommandControllerTest {
    protected final static CallbackContainer callbackContainerMock = Mockito.mock((CallbackContainer.class));
    protected final static MenuCommand menuCommandMock = Mockito.mock(MenuCommand.class);
    protected final static AccountsCallback accountsCallbackMock = Mockito.mock(AccountsCallback.class);

    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
        Mockito.when(callbackContainerMock.retrieve(MENU.getName())).thenReturn(menuCommandMock);
        Mockito.when(callbackContainerMock.retrieve(ACCOUNTS.getName())).thenReturn(accountsCallbackMock);
    }
}
