package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.dialogs.AbstractMainDialogImplTest;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.add.AddConfirmDialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.add.AddContainer;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.add.AddSaveDialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.add.AddStartDialog;
import komrachkov.anton.mybudget.bots.telegram.util.CommandNames;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.Dialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackNamesImpl.ACCOUNT;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AbstractAccountDialogTest.TEST_TYPE_ACCOUNT_ID;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.*;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogIndex.FIRST_STEP_INDEX;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName.*;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for account.AccountDialog")
public class AccountDialogTest extends AbstractMainDialogImplTest {
    // TODO: Change extends class to AbstractAccountDialogTest
    public static final CommandNames TEST_COMMAND_NAME = ACCOUNT;
    private final static int TEST_ADD_ID = 666;

    private static String ADD_ACCOUNT_CALLBACK_PATTERN;

    private AccountDialog accountDialog;

    protected final static AddContainer ADD_CONTAINER_MOCK = Mockito.mock(AddContainer.class);

    protected final static AddStartDialog START_ADD_ACCOUNT_DIALOG_MOCK = Mockito.mock(AddStartDialog.class);
    protected final static TypeDialog typeDialogMock = Mockito.mock(TypeDialog.class);
    protected final static TitleDialog titleDialogMock = Mockito.mock(TitleDialog.class);
    protected final static DescriptionDialog descriptionDialogMock = Mockito.mock(DescriptionDialog.class);
    protected final static CurrencyDialog currencyDialogMock = Mockito.mock(CurrencyDialog.class);
    protected final static BankDialog bankDialogMock = Mockito.mock(BankDialog.class);
    protected final static StartBalanceDialog startBalanceDialogMock = Mockito.mock(StartBalanceDialog.class);
    protected final static AddConfirmDialog CONFIRM_ADD_ACCOUNT_DIALOG_MOCK = Mockito.mock(AddConfirmDialog.class);
    protected final static AddSaveDialog ADD_ACCOUNT_SAVE_DIALOG_MOCK = Mockito.mock(AddSaveDialog.class);


    @BeforeAll
    public static void beforeAll() {
        ADD_ACCOUNT_CALLBACK_PATTERN = String.format(CALLBACK_DIALOG_PATTERN, "%s", ADD_ACCOUNT.getName(), "%s", "%s");

        Mockito.when(ADD_CONTAINER_MOCK.retrieve(START.getName())).thenReturn(START_ADD_ACCOUNT_DIALOG_MOCK);
        Mockito.when(ADD_CONTAINER_MOCK.retrieve(TYPE.getName())).thenReturn(typeDialogMock);
        Mockito.when(ADD_CONTAINER_MOCK.retrieve(TITLE.getName())).thenReturn(titleDialogMock);
        Mockito.when(ADD_CONTAINER_MOCK.retrieve(DESCRIPTION.getName())).thenReturn(descriptionDialogMock);
        Mockito.when(ADD_CONTAINER_MOCK.retrieve(CURRENCY.getName())).thenReturn(currencyDialogMock);
        Mockito.when(ADD_CONTAINER_MOCK.retrieve(BANK.getName())).thenReturn(bankDialogMock);
        Mockito.when(ADD_CONTAINER_MOCK.retrieve(START_BALANCE.getName())).thenReturn(startBalanceDialogMock);
        Mockito.when(ADD_CONTAINER_MOCK.retrieve(CONFIRM.getName())).thenReturn(CONFIRM_ADD_ACCOUNT_DIALOG_MOCK);
        Mockito.when(ADD_CONTAINER_MOCK.retrieve(SAVE.getName())).thenReturn(ADD_ACCOUNT_SAVE_DIALOG_MOCK);

        Mockito.when(START_ADD_ACCOUNT_DIALOG_MOCK.commit(Mockito.any(Update.class))).thenReturn(true);
        Mockito.when(typeDialogMock.commit(Mockito.any(Update.class))).thenReturn(true);
        Mockito.when(titleDialogMock.commit(Mockito.any(Update.class))).thenReturn(true);
        Mockito.when(descriptionDialogMock.commit(Mockito.any(Update.class))).thenReturn(true);
        Mockito.when(currencyDialogMock.commit(Mockito.any(Update.class))).thenReturn(true);
        Mockito.when(bankDialogMock.commit(Mockito.any(Update.class))).thenReturn(true);
        Mockito.when(startBalanceDialogMock.commit(Mockito.any(Update.class))).thenReturn(true);
        Mockito.when(CONFIRM_ADD_ACCOUNT_DIALOG_MOCK.commit(Mockito.any(Update.class))).thenReturn(true);
        Mockito.when(ADD_ACCOUNT_SAVE_DIALOG_MOCK.commit(Mockito.any(Update.class))).thenReturn(true);
    }

    @Override
    @BeforeEach
    public void beforeEach() {
        super.beforeEach();

        this.accountDialog = new AccountDialog(botMessageServiceMock, telegramUserServiceMock, ADD_CONTAINER_MOCK);
    }

    @Test
    public void shouldProperlyExecuteStartDialogCommitAndTypeExecute() {
        //given
        int dialogStep = 0;
        DialogsState.removeAllDialogs(TEST_CHAT_ID);
        DialogsState.put(TEST_CHAT_ID, TEST_COMMAND_NAME, TYPE.getName(), String.valueOf(TEST_TYPE_ACCOUNT_ID));
        DialogsState.put(TEST_CHAT_ID, TEST_COMMAND_NAME, LAST_STEP.getId(), String.valueOf(dialogStep));
        Update update = getCallbackUpdate(String.format(ADD_ACCOUNT_CALLBACK_PATTERN, "from", START.getName(), "start"));

        //when
        accountDialog.execute(update);

        //then
        Mockito.verify(START_ADD_ACCOUNT_DIALOG_MOCK).commit(update);
        Mockito.verify(typeDialogMock).execute(update);
    }

    @ParameterizedTest
    @MethodSource("sourceCommitExecute")
    public void shouldProperlyExecuteCommitExecute(Update update, int dialogStep, AccountNames addAccountName,
                                                   Dialog commitDialogMock, Dialog executeDialogMock) {
        //given
        DialogsState.removeAllDialogs(TEST_CHAT_ID);
        DialogsState.put(TEST_CHAT_ID, TEST_COMMAND_NAME, TYPE.getName(), String.valueOf(TEST_TYPE_ACCOUNT_ID));
        DialogsState.put(TEST_CHAT_ID, TEST_COMMAND_NAME, CURRENT_DIALOG_STEP.getId(), String.valueOf(addAccountName.ordinal()));
        DialogsState.put(TEST_CHAT_ID, TEST_COMMAND_NAME, LAST_STEP.getId(), String.valueOf(dialogStep));

        //when
        accountDialog.execute(update);

        //then
        Mockito.verify(commitDialogMock).commit(update);
        Mockito.verify(executeDialogMock).execute(update);
    }

    private static Stream<Arguments> sourceCommitExecute() {
        List<Arguments> arguments = new ArrayList<>();
        for (int i = FIRST_STEP_INDEX.ordinal() + 1; i + 1 < AccountNames.values().length - 1; i++) {
            arguments.add(Arguments.of(getCallbackUpdate(AccountNames.values()[i]), i, AccountNames.values()[i],
                    ADD_CONTAINER_MOCK.retrieve(AccountNames.values()[i].getName()),
                    ADD_CONTAINER_MOCK.retrieve(AccountNames.values()[i + 1].getName())));
        }
        return arguments.stream();
    }

    private static Update getCallbackUpdate(AccountNames addAccountName) {
        return getCallbackUpdate(String.format(ADD_ACCOUNT_CALLBACK_PATTERN, ADD_ACCOUNT.getName(),
                addAccountName.getName(), TEST_ADD_ID));
    }


    @Test
    public void shouldProperlyExecuteWithExecuteMode() {
        // TODO: Write it!
    }
}
