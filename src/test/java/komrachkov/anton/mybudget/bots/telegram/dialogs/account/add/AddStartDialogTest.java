package komrachkov.anton.mybudget.bots.telegram.dialogs.account.add;

import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl;
import org.junit.jupiter.api.DisplayName;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.AbstractStartDialogTest;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.Dialog;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for AddAccountSaveDialog")
public class AddStartDialogTest extends AbstractStartDialogTest {

    @Override
    public Dialog getCommand() {
        return new AddStartDialog(botMessageServiceMock, telegramUserServiceMock, messageTextMock, keyboardMock,
                DialogNamesImpl.ADD_ACCOUNT);
    }
}
