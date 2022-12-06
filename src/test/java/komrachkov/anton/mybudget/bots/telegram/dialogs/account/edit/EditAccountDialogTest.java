package komrachkov.anton.mybudget.bots.telegram.dialogs.account.edit;

import komrachkov.anton.mybudget.bots.telegram.dialogs.account.*;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.add.*;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogStepsContainer;
import komrachkov.anton.mybudget.bots.telegram.util.CommandNames;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl.EDIT_ACCOUNT;

/**
 * @author Anton Komrachkov
 * @since 0.4 (06.12.2022)
 */

public class EditAccountDialogTest extends AccountDialogTest {
    private final static EditStartDialog editStartDialogMock = Mockito.mock(EditStartDialog.class);
    private final static EditConfirmDialog editConfirmDialogMock = Mockito.mock(EditConfirmDialog.class);
    private final static EditSaveDialog editSaveDialogMock = Mockito.mock(EditSaveDialog.class);

    @Override
    @BeforeEach
    public void beforeEach() {
        super.beforeEach();

    }

    @Override
    protected AccountDialog getAccountDialog() {
        EditContainer addContainerMock = (EditContainer) commandContainerMock;
        return new EditAccountDialog(telegramUserServiceMock, addContainerMock);
    }

    @Override
    protected DialogStepsContainer getCommandContainer() {
        return Mockito.mock(EditContainer.class);
    }

    @Override
    protected StartDialog getStartDialogMock() {
        return editStartDialogMock;
    }

    @Override
    protected ConfirmDialog getConfirmDialogMock() {
        return editConfirmDialogMock;
    }

    @Override
    protected SaveDialog getSaveDialogMock() {
        return editSaveDialogMock;
    }

    @Override
    protected CommandNames getDialogName() {
        return EDIT_ACCOUNT;
    }
}
