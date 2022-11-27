package ru.kas.myBudget.bots.telegram.dialogs.account.addAccount;

import org.junit.jupiter.api.DisplayName;
import ru.kas.myBudget.bots.telegram.dialogs.*;
import ru.kas.myBudget.bots.telegram.dialogs.account.AbstractStartDialogTest;
import ru.kas.myBudget.bots.telegram.dialogs.util.Dialog;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for AddAccountSaveDialog")
public class AddAccountStartDialogTest extends AbstractStartDialogTest {

    @Override
    public Dialog getCommand() {
        return new AddAccountStartDialog(botMessageServiceMock, telegramUserServiceMock, messageTextMock, keyboardMock,
                DialogNamesImpl.ADD_ACCOUNT);
    }
}
