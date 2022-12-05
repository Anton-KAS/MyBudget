package komrachkov.anton.mybudget.bots.telegram.dialogs.account.edit;

import komrachkov.anton.mybudget.bots.telegram.dialogs.account.AbstractSaveDialogTest;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.SaveKeyboard;
import komrachkov.anton.mybudget.models.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.Dialog;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for EditAccountSaveDialog")
public class EditSaveDialogTest extends AbstractSaveDialogTest {
    private final static SaveKeyboard saveKeyboardMock = Mockito.mock(SaveKeyboard.class);

    @Override
    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
        Mockito.when(accountServiceMock.findById(TEST_ACCOUNT_ID)).thenReturn(Optional.of(getTestAccount()));
    }

    @Override
    protected Dialog getCommand() {
        return new EditSaveDialog(telegramUserServiceMock, accountTextMock, saveKeyboardMock,
                callbackContainerMock, accountTypeServiceMock, currencyServiceMock, bankServiceMock, accountServiceMock);
    }

    @Override
    protected Account getExpectedAccount() {
        return new Account(TEST_ACCOUNT_ID, TEST_TITLE_TEXT, TEST_DESCRIPTION_TEXT, TEST_START_BALANCE_BASIC,
                TEST_START_BALANCE_BASIC, TEST_DATE, TEST_DATE, telegramUserMock, currencyMock, accountTypeMock,
                bankMock);
    }

    @Override
    protected Account getTestAccount() {
        return new Account(TEST_ACCOUNT_ID, "", "", new BigDecimal(0), new BigDecimal(0),
                TEST_DATE, TEST_DATE, telegramUserMock, null, null, null);
    }
}
