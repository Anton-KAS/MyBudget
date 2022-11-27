package ru.kas.myBudget.bots.telegram.dialogs.account.addAccount;

import org.junit.jupiter.api.DisplayName;
import ru.kas.myBudget.bots.telegram.dialogs.account.AbstractSaveDialogTest;
import ru.kas.myBudget.bots.telegram.dialogs.util.Dialog;
import ru.kas.myBudget.models.Account;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for AddAccountSaveDialog")
public class AddAccountSaveDialogTest extends AbstractSaveDialogTest {

    @Override
    protected Dialog getCommand() {
        return new AddAccountSaveDialog(botMessageServiceMock, telegramUserServiceMock, messageTextMock, keyboardMock,
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
