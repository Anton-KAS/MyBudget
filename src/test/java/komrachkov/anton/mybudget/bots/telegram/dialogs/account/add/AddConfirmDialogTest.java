package komrachkov.anton.mybudget.bots.telegram.dialogs.account.add;

import komrachkov.anton.mybudget.bots.telegram.dialogs.account.AbstractConfirmDialogTest;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.Dialog;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.add.AddConfirmKeyboard;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;

/**
 * Unit-tests for {@link AddConfirmDialog}
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for dialog.account.AddConfirmDialog")
public class AddConfirmDialogTest extends AbstractConfirmDialogTest {
    private final AddConfirmKeyboard addConfirmKeyboardMock = Mockito.mock(AddConfirmKeyboard.class);

    @Override
    public Dialog getCommand() {
        return new AddConfirmDialog(telegramUserServiceMock, accountTextMock, addConfirmKeyboardMock);
    }
}
