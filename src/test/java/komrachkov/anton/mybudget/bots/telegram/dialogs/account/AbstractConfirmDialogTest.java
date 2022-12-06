package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.dialogs.util.CommandDialogNames;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.CONFIRM;

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
        return accountTextMock;
    }

    @Override
    public void shouldReturnTrueByExecuteCommit(Update update) {
        //TODO: Write test
    }
}
