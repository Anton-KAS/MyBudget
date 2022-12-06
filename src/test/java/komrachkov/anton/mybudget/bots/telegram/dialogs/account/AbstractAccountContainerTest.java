package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.commands.UnknownCommand;
import komrachkov.anton.mybudget.bots.telegram.dialogs.UnknownDialog;
import komrachkov.anton.mybudget.bots.telegram.util.AbstractContainerTest;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public abstract class AbstractAccountContainerTest extends AbstractContainerTest {
    protected final static TypeDialog typeDialogMock = Mockito.mock(TypeDialog.class);
    protected final static TitleDialog titleDialogMock = Mockito.mock(TitleDialog.class);
    protected final static DescriptionDialog descriptionDialogMock = Mockito.mock(DescriptionDialog.class);
    protected final static CurrencyDialog currencyDialogMock = Mockito.mock(CurrencyDialog.class);
    protected final static BankDialog bankDialogMock = Mockito.mock(BankDialog.class);
    protected final static StartBalanceDialog startBalanceDialogMock = Mockito.mock(StartBalanceDialog.class);
    protected final static UnknownDialog unknownDialogMock = Mockito.mock(UnknownDialog.class);

    protected static String testDialogName;

    abstract protected void setTestDialogName();

    @Override
    @BeforeEach
    public void beforeEach() {
        setTestDialogName();

        Mockito.when(typeDialogMock.setCurrentDialogName(testDialogName)).thenReturn(typeDialogMock);
        Mockito.when(titleDialogMock.setCurrentDialogName(testDialogName)).thenReturn(titleDialogMock);
        Mockito.when(descriptionDialogMock.setCurrentDialogName(testDialogName)).thenReturn(descriptionDialogMock);
        Mockito.when(currencyDialogMock.setCurrentDialogName(testDialogName)).thenReturn(currencyDialogMock);
        Mockito.when(bankDialogMock.setCurrentDialogName(testDialogName)).thenReturn(bankDialogMock);
        Mockito.when(startBalanceDialogMock.setCurrentDialogName(testDialogName)).thenReturn(startBalanceDialogMock);
        Mockito.when(unknownDialogMock.setCurrentDialogName(testDialogName)).thenReturn(unknownDialogMock);
    }

    @Override
    protected void setNames() {
        commandNames = AccountNames.values();
    }

    @Override
    protected void setUnknownCommand() {
        unknownCommand = Mockito.mock(UnknownDialog.class);
    }
}
