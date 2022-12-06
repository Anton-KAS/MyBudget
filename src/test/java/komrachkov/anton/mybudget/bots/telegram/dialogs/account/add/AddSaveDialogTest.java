package komrachkov.anton.mybudget.bots.telegram.dialogs.account.add;

import komrachkov.anton.mybudget.bots.telegram.dialogs.account.AbstractSaveDialogTest;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.SaveKeyboard;
import komrachkov.anton.mybudget.models.Account;
import org.junit.jupiter.api.DisplayName;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.Dialog;
import org.mockito.Mockito;

/**
 * Unit-tests for {@link AddSaveDialog}
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for dialog.account.AddSaveDialog")
public class AddSaveDialogTest extends AbstractSaveDialogTest {
    private final SaveKeyboard saveKeyboardMock = Mockito.mock(SaveKeyboard.class);

    @Override
    protected Dialog getCommand() {
        return new AddSaveDialog(telegramUserServiceMock, accountTextMock, saveKeyboardMock,
                callbackContainerMock, accountTypeServiceMock, currencyServiceMock, bankServiceMock, accountServiceMock);
    }

    @Override
    protected Account getExpectedAccount() {
        return new Account(TEST_TITLE_TEXT, TEST_DESCRIPTION_TEXT, TEST_START_BALANCE_BASIC,
                TEST_START_BALANCE_BASIC, telegramUserMock, currencyMock, accountTypeMock, bankMock);
    }

    @Override
    protected Account getTestAccount() {
        return null;
    }
}
