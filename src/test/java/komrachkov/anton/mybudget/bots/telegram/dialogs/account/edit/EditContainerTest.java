package komrachkov.anton.mybudget.bots.telegram.dialogs.account.edit;

import org.junit.jupiter.api.DisplayName;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.AbstractAccountContainerTest;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for EditAccountContainer")
public class EditContainerTest extends AbstractAccountContainerTest {

    @Override
    protected void setContainer() {
        container = new EditContainer(botMessageServiceMock, telegramUserServiceMock, callbackContainerMock,
                accountTypeServiceMock, currencyServiceMock, bankServiceMock, accountServiceMock);
    }
}
