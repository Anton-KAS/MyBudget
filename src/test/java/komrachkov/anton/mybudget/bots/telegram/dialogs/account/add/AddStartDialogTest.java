package komrachkov.anton.mybudget.bots.telegram.dialogs.account.add;

import org.junit.jupiter.api.DisplayName;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.AbstractStartDialogTest;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.Dialog;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;

/**
 * Unit-tests for {@link AddStartDialog}
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for dialog.account.AddStartDialog")
public class AddStartDialogTest extends AbstractStartDialogTest {

    @Override
    public Dialog getCommand() {
        return new AddStartDialog(telegramUserServiceMock, accountTextMock);
    }

    @Override
    protected String getTestDialogName() {
        return ADD_ACCOUNT.getName();
    }
}
