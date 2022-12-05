package komrachkov.anton.mybudget.bots.telegram.dialogs.account.edit;

import komrachkov.anton.mybudget.bots.telegram.dialogs.account.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl.EDIT_ACCOUNT;

/**
 * Unit-tests for {@link EditContainer}
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for EditAccountContainer")
public class EditContainerTest extends AbstractAccountContainerTest {
    private final static EditStartDialog editStartDialogMock = Mockito.mock(EditStartDialog.class);
    private final static EditConfirmDialog editConfirmDialogMock = Mockito.mock(EditConfirmDialog.class);
    private final static EditSaveDialog editSaveDialogMock = Mockito.mock(EditSaveDialog.class);

    @Override
    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
        Mockito.when(editStartDialogMock.setCurrentDialogName(testDialogName)).thenReturn(editStartDialogMock);
        Mockito.when(editConfirmDialogMock.setCurrentDialogName(testDialogName)).thenReturn(editConfirmDialogMock);
        Mockito.when(editSaveDialogMock.setCurrentDialogName(testDialogName)).thenReturn(editSaveDialogMock);

        init();
    }

    @Override
    protected void setContainer() {
        Mockito.when(editStartDialogMock.setCurrentDialogName(testDialogName)).thenReturn(editStartDialogMock);
        container = new EditContainer(editStartDialogMock, typeDialogMock, titleDialogMock, descriptionDialogMock,
                currencyDialogMock, bankDialogMock, startBalanceDialogMock, editConfirmDialogMock, editSaveDialogMock,
                unknownDialogMock);
    }

    @Override
    protected void setTestDialogName() {
        this.testDialogName = EDIT_ACCOUNT.getName();
    }
}
