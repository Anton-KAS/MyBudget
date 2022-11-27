package ru.kas.myBudget.bots.telegram.dialogs.account.editAccount;

import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import ru.kas.myBudget.bots.telegram.dialogs.account.AbstractConfirmDialogTest;
import ru.kas.myBudget.bots.telegram.dialogs.util.Dialog;
import ru.kas.myBudget.bots.telegram.keyboards.accountDialog.editAccountDialog.EditAccountConfirmKeyboard;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for EditAccountConfirmDialog")
public class EditAccountConfirmDialogTest extends AbstractConfirmDialogTest {
    private final static EditAccountConfirmKeyboard editAccountConfirmKeyboardMock = Mockito.mock(EditAccountConfirmKeyboard.class);

    @Override
    public Dialog getCommand() {
        return new EditAccountConfirmDialog(botMessageServiceMock, telegramUserServiceMock, messageTextMock, editAccountConfirmKeyboardMock);
    }
}
