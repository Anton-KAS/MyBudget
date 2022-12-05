package komrachkov.anton.mybudget.bots.telegram.dialogs;

import komrachkov.anton.mybudget.bots.telegram.dialogs.account.add.AddAccountDialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.edit.EditAccountDialog;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import komrachkov.anton.mybudget.bots.telegram.util.AbstractContainerTest;

/**
 * Unit-tests for {@link DialogContainer}
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for DialogContainer")
public class DialogContainerTest extends AbstractContainerTest {
    private final static UnknownDialog unknownDialogMock = Mockito.mock(UnknownDialog.class);
    private final static DeleteConfirmDialog deleteConfirmDialogMock = Mockito.mock(DeleteConfirmDialog.class);
    private final static DeleteExecuteDialog deleteExecuteDialogMock = Mockito.mock(DeleteExecuteDialog.class);
    private final static AddAccountDialog addAccountDialogMock = Mockito.mock(AddAccountDialog.class);
    private final static EditAccountDialog editAccountDialogMock = Mockito.mock(EditAccountDialog.class);
    private final static CancelDialog cancelDialogMock = Mockito.mock(CancelDialog.class);

    @Override
    protected void setContainer() {
        container = new DialogContainer(unknownDialogMock, deleteConfirmDialogMock, deleteExecuteDialogMock,
                addAccountDialogMock, editAccountDialogMock, cancelDialogMock);
    }

    @Override
    protected void setNames() {
        commandNames = DialogNamesImpl.values();
    }

    @Override
    protected void setUnknownCommand() {
        unknownCommand = Mockito.mock(UnknownDialog.class);
    }
}
