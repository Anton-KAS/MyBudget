package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import ru.kas.myBudget.bots.telegram.dialogs.AbstractDialogImplTest;
import ru.kas.myBudget.bots.telegram.dialogs.AddAccount.ConfirmDialog;
import ru.kas.myBudget.bots.telegram.dialogs.CommandDialogNames;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.texts.addAccountDialog.AddAccountText;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountNames.CONFIRM;

@DisplayName("Unit-level testing for ConfirmDialog")
public class ConfirmDialogTest extends AbstractDialogImplTest {
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
        return new ConfirmDialog(botMessageServiceMock, telegramUserServiceMock, messageTextMock, keyboardMock, dialogsMapMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(AddAccountText.class);
    }
}
