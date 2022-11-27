package ru.kas.myBudget.bots.telegram.dialogs.account.editAccount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import ru.kas.myBudget.bots.telegram.dialogs.account.AbstractSaveDialogTest;
import ru.kas.myBudget.bots.telegram.dialogs.util.Dialog;
import ru.kas.myBudget.models.Account;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for EditAccountSaveDialog")
public class EditAccountSaveDialogTest extends AbstractSaveDialogTest {

    @Override
    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
        Mockito.when(accountServiceMock.findById(TEST_ACCOUNT_ID)).thenReturn(Optional.of(getTestAccount()));
    }

    @Override
    protected Dialog getCommand() {
        return new EditAccountSaveDialog(botMessageServiceMock, telegramUserServiceMock, messageTextMock, keyboardMock,
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
