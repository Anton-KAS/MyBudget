package ru.kas.myBudget.bots.telegram.dialogs.account.editAccount;

import org.junit.jupiter.api.DisplayName;
import ru.kas.myBudget.bots.telegram.dialogs.account.AbstractAccountContainerTest;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for EditAccountContainer")
public class EditAccountContainerTest extends AbstractAccountContainerTest {

    @Override
    protected void setContainer() {
        container = new EditAccountContainer(botMessageServiceMock, telegramUserServiceMock, callbackContainerMock,
                accountTypeServiceMock, currencyServiceMock, bankServiceMock, accountServiceMock);
    }
}
