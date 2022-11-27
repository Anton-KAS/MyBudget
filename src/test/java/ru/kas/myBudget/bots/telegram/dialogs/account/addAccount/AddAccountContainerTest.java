package ru.kas.myBudget.bots.telegram.dialogs.account.addAccount;

import org.junit.jupiter.api.DisplayName;
import ru.kas.myBudget.bots.telegram.dialogs.account.AbstractAccountContainerTest;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for AddAccountContainer")
public class AddAccountContainerTest extends AbstractAccountContainerTest {

    @Override
    protected void setContainer() {
        container = new AddAccountContainer(botMessageServiceMock, telegramUserServiceMock, callbackContainerMock,
                accountTypeServiceMock, currencyServiceMock, bankServiceMock, accountServiceMock);
    }
}
