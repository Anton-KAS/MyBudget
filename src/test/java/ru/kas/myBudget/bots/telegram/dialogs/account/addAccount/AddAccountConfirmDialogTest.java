package ru.kas.myBudget.bots.telegram.dialogs.account.addAccount;

import org.junit.jupiter.api.DisplayName;
import ru.kas.myBudget.bots.telegram.dialogs.account.AbstractConfirmDialogTest;
import ru.kas.myBudget.bots.telegram.dialogs.util.Dialog;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for AddAccountConfirmDialog")
public class AddAccountConfirmDialogTest extends AbstractConfirmDialogTest {

    @Override
    public Dialog getCommand() {
        return new AddAccountConfirmDialog(botMessageServiceMock, telegramUserServiceMock, messageTextMock, keyboardMock);
    }
}
