package komrachkov.anton.mybudget.bots.telegram.dialogs.account.add;

import org.junit.jupiter.api.DisplayName;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.AbstractAccountContainerTest;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for AddAccountContainer")
public class AddContainerTest extends AbstractAccountContainerTest {

    @Override
    protected void setContainer() {
        container = new AddContainer(botMessageServiceMock, telegramUserServiceMock, callbackContainerMock,
                accountTypeServiceMock, currencyServiceMock, bankServiceMock, accountServiceMock);
    }
}
