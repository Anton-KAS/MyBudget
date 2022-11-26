package ru.kas.myBudget.bots.telegram.dialogs.account;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.AbstractMainDialogImplTest;
import ru.kas.myBudget.bots.telegram.dialogs.account.addAccount.*;
import ru.kas.myBudget.bots.telegram.dialogs.util.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.util.DialogsMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static ru.kas.myBudget.bots.telegram.dialogs.account.AccountNames.*;
import static ru.kas.myBudget.bots.telegram.dialogs.util.DialogIndex.FIRST_STEP_INDEX;
import static ru.kas.myBudget.bots.telegram.dialogs.util.DialogMapDefaultName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for account.AddAccountDialog")
public class AccountDialogTest extends AbstractMainDialogImplTest {
    private final static int TEST_ADD_ID = 666;
    private final static String TEST_TYPE_ID = "TEST TYPE ID";

    private static String ADD_ACCOUNT_CALLBACK_PATTERN;

    private AccountDialog accountDialog;

    protected final static AddAccountContainer addAccountContainerMock = Mockito.mock(AddAccountContainer.class);

    protected final static AddAccountStartDialog START_ADD_ACCOUNT_DIALOG_MOCK = Mockito.mock(AddAccountStartDialog.class);
    protected final static TypeDialog typeDialogMock = Mockito.mock(TypeDialog.class);
    protected final static TitleDialog titleDialogMock = Mockito.mock(TitleDialog.class);
    protected final static DescriptionDialog descriptionDialogMock = Mockito.mock(DescriptionDialog.class);
    protected final static CurrencyDialog currencyDialogMock = Mockito.mock(CurrencyDialog.class);
    protected final static BankDialog bankDialogMock = Mockito.mock(BankDialog.class);
    protected final static StartBalanceDialog startBalanceDialogMock = Mockito.mock(StartBalanceDialog.class);
    protected final static AddAccountConfirmDialog CONFIRM_ADD_ACCOUNT_DIALOG_MOCK = Mockito.mock(AddAccountConfirmDialog.class);
    protected final static AddAccountSaveDialog ADD_ACCOUNT_SAVE_DIALOG_MOCK = Mockito.mock(AddAccountSaveDialog.class);


    @BeforeAll
    public static void beforeAll() {
        ADD_ACCOUNT_CALLBACK_PATTERN = String.format(CALLBACK_DIALOG_PATTERN, "%s", ADD_ACCOUNT.getName(), "%s", "%s");

        Mockito.when(addAccountContainerMock.retrieve(START.getName())).thenReturn(START_ADD_ACCOUNT_DIALOG_MOCK);
        Mockito.when(addAccountContainerMock.retrieve(TYPE.getName())).thenReturn(typeDialogMock);
        Mockito.when(addAccountContainerMock.retrieve(TITLE.getName())).thenReturn(titleDialogMock);
        Mockito.when(addAccountContainerMock.retrieve(DESCRIPTION.getName())).thenReturn(descriptionDialogMock);
        Mockito.when(addAccountContainerMock.retrieve(CURRENCY.getName())).thenReturn(currencyDialogMock);
        Mockito.when(addAccountContainerMock.retrieve(BANK.getName())).thenReturn(bankDialogMock);
        Mockito.when(addAccountContainerMock.retrieve(START_BALANCE.getName())).thenReturn(startBalanceDialogMock);
        Mockito.when(addAccountContainerMock.retrieve(CONFIRM.getName())).thenReturn(CONFIRM_ADD_ACCOUNT_DIALOG_MOCK);
        Mockito.when(addAccountContainerMock.retrieve(SAVE.getName())).thenReturn(ADD_ACCOUNT_SAVE_DIALOG_MOCK);

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

        this.accountDialog = new AccountDialog(botMessageServiceMock, telegramUserServiceMock, addAccountContainerMock);
    }

    @Test
    public void shouldProperlyExecuteStartDialogCommitAndTypeExecute() {
        //given
        int dialogStep = 0;
        DialogsMap.remove(TEST_CHAT_ID);
        DialogsMap.put(TEST_CHAT_ID, TYPE.getName(), TEST_TYPE_ID);
        DialogsMap.put(TEST_CHAT_ID, LAST_STEP.getId(), String.valueOf(dialogStep));
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
        DialogsMap.remove(TEST_CHAT_ID);
        DialogsMap.put(TEST_CHAT_ID, TYPE.getName(), TEST_TYPE_ID);
        DialogsMap.put(TEST_CHAT_ID, CURRENT_DIALOG_STEP.getId(), String.valueOf(addAccountName.ordinal()));
        DialogsMap.put(TEST_CHAT_ID, LAST_STEP.getId(), String.valueOf(dialogStep));

        //when
        accountDialog.execute(update);

        //then
        Mockito.verify(commitDialogMock).commit(update);
        Mockito.verify(executeDialogMock).execute(update);
    }

    private static Stream<Arguments> sourceCommitExecute() {
        //int dialogStep = FIRST_STEP_INDEX.getIndex() + 1;
        List<Arguments> arguments = new ArrayList<>();
        for (int i = FIRST_STEP_INDEX.ordinal() + 1; i + 1 < AccountNames.values().length - 1; i++) {
            arguments.add(Arguments.of(getCallbackUpdate(AccountNames.values()[i]), i, AccountNames.values()[i],
                    addAccountContainerMock.retrieve(AccountNames.values()[i].getName()),
                    addAccountContainerMock.retrieve(AccountNames.values()[i+1].getName())));
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
