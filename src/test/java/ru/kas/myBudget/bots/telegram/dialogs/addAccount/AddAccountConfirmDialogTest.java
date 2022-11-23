package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import ru.kas.myBudget.bots.telegram.dialogs.AbstractDialogImplTest;
import ru.kas.myBudget.bots.telegram.dialogs.account.addAccount.AddAccountConfirmDialog;
import ru.kas.myBudget.bots.telegram.dialogs.util.CommandDialogNames;
import ru.kas.myBudget.bots.telegram.dialogs.util.Dialog;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.texts.accountDialog.AccountText;

import static ru.kas.myBudget.bots.telegram.dialogs.account.AccountNames.CONFIRM;

@DisplayName("Unit-level testing for AddAccount.ConfirmDialog")
public class AddAccountConfirmDialogTest extends AbstractDialogImplTest {
    @Override
    protected String getCommandName() {
        return CONFIRM.getName();
    }

    @Override
    public CommandDialogNames getCommandDialogName() {
        return CONFIRM;
    }

    @Override
    public Dialog getCommand() {
        return new AddAccountConfirmDialog(botMessageServiceMock, telegramUserServiceMock, messageTextMock, keyboardMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(AccountText.class);
    }
}
