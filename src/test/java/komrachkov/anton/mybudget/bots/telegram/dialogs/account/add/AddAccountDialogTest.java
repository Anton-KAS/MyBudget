package komrachkov.anton.mybudget.bots.telegram.dialogs.account.add;

import komrachkov.anton.mybudget.bots.telegram.dialogs.account.*;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogStepsContainer;
import komrachkov.anton.mybudget.bots.telegram.util.CommandNames;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;

/**
 * @author Anton Komrachkov
 * @since 0.4 (06.12.2022)
 */

public class AddAccountDialogTest extends AccountDialogTest {
    private final static AddStartDialog addStartDialogMock = Mockito.mock(AddStartDialog.class);
    private final static AddConfirmDialog addConfirmDialogMock = Mockito.mock(AddConfirmDialog.class);
    private final static AddSaveDialog addSaveDialogMock = Mockito.mock(AddSaveDialog.class);

    @Override
    @BeforeEach
    public void beforeEach() {
        super.beforeEach();

    }

    @Override
    protected AccountDialog getAccountDialog() {
        AddContainer addContainerMock = (AddContainer) commandContainerMock;
        return new AddAccountDialog(telegramUserServiceMock, addContainerMock);
    }

    @Override
    protected DialogStepsContainer getCommandContainer() {
        return Mockito.mock(AddContainer.class);
    }

    @Override
    protected StartDialog getStartDialogMock() {
        return addStartDialogMock;
    }

    @Override
    protected ConfirmDialog getConfirmDialogMock() {
        return addConfirmDialogMock;
    }

    @Override
    protected SaveDialog getSaveDialogMock() {
        return addSaveDialogMock;
    }

    @Override
    protected CommandNames getDialogName() {
        return ADD_ACCOUNT;
    }
}
