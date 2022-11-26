package ru.kas.myBudget.bots.telegram.dialogs.account.addAccount;

import org.junit.jupiter.api.DisplayName;
import ru.kas.myBudget.bots.telegram.dialogs.account.SaveDialogTest;
import ru.kas.myBudget.bots.telegram.dialogs.util.Dialog;
import ru.kas.myBudget.models.Account;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for account...AddAccountSaveDialog")
public class AddAccountSaveDialogTest extends SaveDialogTest {

    @Override
    protected Dialog getCommand() {
        return new AddAccountSaveDialog(botMessageServiceMock, telegramUserServiceMock, messageTextMock, keyboardMock,
                callbackContainerMock, accountTypeServiceMock, currencyServiceMock, bankServiceMock, accountServiceMock);
    }

    @Override
    protected Account getExpectedAccount() {
        return new Account(TEST_TITLE_TEXT, TEST_DESCRIPTION_TEXT, TEST_START_BALANCE_DIALOG_MAP,
                TEST_START_BALANCE_DIALOG_MAP, telegramUserMock, currencyMock, accountTypeMock, bankMock);
    }

    @Override
    protected Account getTestAccount() {
        return null;
    }
}
