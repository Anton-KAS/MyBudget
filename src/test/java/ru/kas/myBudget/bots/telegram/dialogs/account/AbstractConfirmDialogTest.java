package ru.kas.myBudget.bots.telegram.dialogs.account;

import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.util.CommandDialogNames;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.texts.accountDialog.AccountText;

import static ru.kas.myBudget.bots.telegram.dialogs.account.AccountNames.CONFIRM;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public abstract class AbstractConfirmDialogTest extends AbstractAccountDialogTest {
    @Override
    protected String getCommandName() {
        return CONFIRM.getName();
    }

    @Override
    public CommandDialogNames getCommandDialogName() {
        return CONFIRM;
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(AccountText.class);
    }

    @Override
    public void shouldReturnTrueByExecuteCommit(Update update) {
        //TODO: Write test
    }
}
