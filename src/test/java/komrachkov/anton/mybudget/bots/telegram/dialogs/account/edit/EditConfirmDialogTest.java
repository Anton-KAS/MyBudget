package komrachkov.anton.mybudget.bots.telegram.dialogs.account.edit;

import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.edit.EditConfirmKeyboard;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.AbstractConfirmDialogTest;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.Dialog;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for EditAccountConfirmDialog")
public class EditConfirmDialogTest extends AbstractConfirmDialogTest {
    private final static EditConfirmKeyboard EDIT_CONFIRM_KEYBOARD_MOCK = Mockito.mock(EditConfirmKeyboard.class);

    @Override
    public Dialog getCommand() {
        return new EditConfirmDialog(telegramUserServiceMock, accountTextMock, EDIT_CONFIRM_KEYBOARD_MOCK);
    }
}
