package ru.kas.myBudget.bots.telegram.dialogs.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.util.CommandDialogNames;
import ru.kas.myBudget.bots.telegram.dialogs.util.DialogsMap;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.texts.accountDialog.AccountText;
import ru.kas.myBudget.models.*;

import java.util.Date;

import static ru.kas.myBudget.bots.telegram.dialogs.account.AccountNames.*;
import static ru.kas.myBudget.bots.telegram.dialogs.util.DialogMapDefaultName.EDIT_ID;

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

        DialogsMap.remove(TEST_CHAT_ID);
        DialogsMap.put(TEST_CHAT_ID, TITLE.getName(), TEST_TITLE_TEXT);
        DialogsMap.put(TEST_CHAT_ID, DESCRIPTION.getName(), TEST_DESCRIPTION_TEXT);
        DialogsMap.put(TEST_CHAT_ID, CURRENCY.getName(), String.valueOf(TEST_CURRENCY_ID));
        DialogsMap.put(TEST_CHAT_ID, TYPE.getName(), String.valueOf(TEST_TYPE_ACCOUNT_ID));
        DialogsMap.put(TEST_CHAT_ID, START_BALANCE.getName(), String.valueOf(TEST_START_BALANCE));
        DialogsMap.put(TEST_CHAT_ID, BANK.getName(), String.valueOf(TEST_BANK_ID));
        DialogsMap.put(TEST_CHAT_ID, EDIT_ID.getId(), String.valueOf(TEST_ACCOUNT_ID));
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
