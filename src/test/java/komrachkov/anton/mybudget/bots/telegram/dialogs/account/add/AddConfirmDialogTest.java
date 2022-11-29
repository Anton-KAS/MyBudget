package komrachkov.anton.mybudget.bots.telegram.dialogs.account.add;

import komrachkov.anton.mybudget.bots.telegram.dialogs.account.AbstractConfirmDialogTest;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.Dialog;
import org.junit.jupiter.api.DisplayName;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for AddAccountConfirmDialog")
public class AddConfirmDialogTest extends AbstractConfirmDialogTest {

    @Override
    public Dialog getCommand() {
        return new AddConfirmDialog(botMessageServiceMock, telegramUserServiceMock, messageTextMock, keyboardMock);
    }
}
