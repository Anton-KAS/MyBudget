package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.dialogs.util.CommandDialogNames;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.models.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackContainer;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;
import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.AccountText;

import java.util.Date;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.*;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName.EDIT_ID;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public abstract class AbstractSaveDialogTest extends AbstractAccountDialogTest {
    public final static Date TEST_DATE = new Date();

    public final static CallbackContainer callbackContainerMock = Mockito.mock(CallbackContainer.class);

    @Override
    protected String getCommandName() {
        return SAVE.getName();
    }

    @Override
    protected CommandDialogNames getCommandDialogName() {
        return SAVE;
    }

    protected abstract Account getExpectedAccount();

    protected abstract Account getTestAccount();

    @Override
    @BeforeEach
    public void beforeEach() {
        super.beforeEach();

        DialogsState.removeAllDialogs(TEST_CHAT_ID);
        DialogsState.put(TEST_CHAT_ID, TEST_COMMAND_NAME, TITLE.getName(), TEST_TITLE_TEXT);
        DialogsState.put(TEST_CHAT_ID, TEST_COMMAND_NAME, DESCRIPTION.getName(), TEST_DESCRIPTION_TEXT);
        DialogsState.put(TEST_CHAT_ID, TEST_COMMAND_NAME, CURRENCY.getName(), String.valueOf(TEST_CURRENCY_ID));
        DialogsState.put(TEST_CHAT_ID, TEST_COMMAND_NAME, TYPE.getName(), String.valueOf(TEST_TYPE_ACCOUNT_ID));
        DialogsState.put(TEST_CHAT_ID, TEST_COMMAND_NAME, START_BALANCE.getName(), String.valueOf(TEST_START_BALANCE));
        DialogsState.put(TEST_CHAT_ID, TEST_COMMAND_NAME, BANK.getName(), String.valueOf(TEST_BANK_ID));
        DialogsState.put(TEST_CHAT_ID, TEST_COMMAND_NAME, EDIT_ID.getId(), String.valueOf(TEST_ACCOUNT_ID));
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(AccountText.class);
    }

    @Test
    public void shouldProperlyExecuteSaveAccount() {
        //given
        Update update = getCallbackUpdateWithData(TEST_DATA);
        Account expectedAccount = getExpectedAccount();

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(accountServiceMock).save(expectedAccount);
    }

    @Override
    public void shouldProperlyExecuteSetUserId() {
    }

    @Override
    public void shouldProperlyExecuteSetUserIdExecuteMode() {
    }

    @Override
    public void shouldProperlyExecuteGetText() {
    }

    @Override
    public void shouldProperlyExecuteGetTextExecuteMode() {
    }

    @Override
    public void shouldProperlyExecuteAndUpdateUser() {
    }

    @Override
    public void shouldProperlyExecuteAndUpdateUserExecuteMode() {
    }
}
