package komrachkov.anton.mybudget.bots.telegram.dialogs.account.add;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.AbstractAccountContainerTest;
import org.mockito.Mockito;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;

/**
 * Unit-tests for {@link AddContainer}
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for dialog.account.AddContainer")
public class AddContainerTest extends AbstractAccountContainerTest {
    private final static AddStartDialog addStartDialogMock = Mockito.mock(AddStartDialog.class);
    private final static AddConfirmDialog addConfirmDialogMock = Mockito.mock(AddConfirmDialog.class);
    private final static AddSaveDialog addSaveDialogMock = Mockito.mock(AddSaveDialog.class);

    @Override
    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
        Mockito.when(addStartDialogMock.setCurrentDialogName(testDialogName)).thenReturn(addStartDialogMock);
        Mockito.when(addConfirmDialogMock.setCurrentDialogName(testDialogName)).thenReturn(addConfirmDialogMock);
        Mockito.when(addSaveDialogMock.setCurrentDialogName(testDialogName)).thenReturn(addSaveDialogMock);
        System.out.println(testDialogName);

        init();
    }

    @Override
    protected void setContainer() {
        container = new AddContainer(addStartDialogMock, typeDialogMock, titleDialogMock, descriptionDialogMock,
                currencyDialogMock, bankDialogMock, startBalanceDialogMock, addConfirmDialogMock, addSaveDialogMock,
                unknownDialogMock);
    }

    @Override
    protected void setTestDialogName() {
        this.testDialogName = ADD_ACCOUNT.getName();
    }
}
