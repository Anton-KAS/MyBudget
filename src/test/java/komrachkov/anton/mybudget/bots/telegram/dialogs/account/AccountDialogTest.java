package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.dialogs.AbstractMainDialogImplTest;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogStepsContainer;
import komrachkov.anton.mybudget.bots.telegram.util.CommandNames;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
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
public abstract class AccountDialogTest extends AbstractMainDialogImplTest {
    // TODO: Change extends class to AbstractAccountDialogTest
    private final static int TEST_ADD_ID = 666;

    private static String accountCallbackPattern;

    protected static AccountDialog accountDialog;
    protected static DialogStepsContainer commandContainerMock;

    protected static StartDialog startDialogMock;
    protected final static TypeDialog typeDialogMock = Mockito.mock(TypeDialog.class);
    protected final static TitleDialog titleDialogMock = Mockito.mock(TitleDialog.class);
    protected final static DescriptionDialog descriptionDialogMock = Mockito.mock(DescriptionDialog.class);
    protected final static CurrencyDialog currencyDialogMock = Mockito.mock(CurrencyDialog.class);
    protected final static BankDialog bankDialogMock = Mockito.mock(BankDialog.class);
    protected final static StartBalanceDialog startBalanceDialogMock = Mockito.mock(StartBalanceDialog.class);
    protected static ConfirmDialog confirmDialogMock;
    protected static SaveDialog saveDialogMock;
    protected static CommandNames dialogName;

    protected abstract AccountDialog getAccountDialog();
    protected abstract DialogStepsContainer getCommandContainer();
    protected abstract StartDialog getStartDialogMock();
    protected abstract ConfirmDialog getConfirmDialogMock();
    protected abstract SaveDialog getSaveDialogMock();
    protected abstract CommandNames getDialogName();

    @Override
    @BeforeEach
    public void beforeEach() {
        startDialogMock = getStartDialogMock();
        confirmDialogMock = getConfirmDialogMock();
        saveDialogMock = getSaveDialogMock();
        commandContainerMock = getCommandContainer();
        accountDialog = getAccountDialog();
        dialogName = getDialogName();

        accountCallbackPattern = String.format(CALLBACK_DIALOG_PATTERN, "%s", dialogName.getName(), "%s", "%s");

        super.beforeEach();

        Mockito.when(commandContainerMock.retrieve(START.getName())).thenReturn(startDialogMock);
        Mockito.when(commandContainerMock.retrieve(TYPE.getName())).thenReturn(typeDialogMock);
        Mockito.when(commandContainerMock.retrieve(TITLE.getName())).thenReturn(titleDialogMock);
        Mockito.when(commandContainerMock.retrieve(DESCRIPTION.getName())).thenReturn(descriptionDialogMock);
        Mockito.when(commandContainerMock.retrieve(CURRENCY.getName())).thenReturn(currencyDialogMock);
        Mockito.when(commandContainerMock.retrieve(BANK.getName())).thenReturn(bankDialogMock);
        Mockito.when(commandContainerMock.retrieve(START_BALANCE.getName())).thenReturn(startBalanceDialogMock);
        Mockito.when(commandContainerMock.retrieve(CONFIRM.getName())).thenReturn(confirmDialogMock);
        Mockito.when(commandContainerMock.retrieve(SAVE.getName())).thenReturn(saveDialogMock);

        ToDoList toDoList = Mockito.mock(ToDoList.class);
        Mockito.when(toDoList.isResultCommit()).thenReturn(true);

        Mockito.when(startDialogMock.commit(Mockito.any(Update.class))).thenReturn(toDoList);
        Mockito.when(typeDialogMock.commit(Mockito.any(Update.class))).thenReturn(toDoList);
        Mockito.when(titleDialogMock.commit(Mockito.any(Update.class))).thenReturn(toDoList);
        Mockito.when(descriptionDialogMock.commit(Mockito.any(Update.class))).thenReturn(toDoList);
        Mockito.when(currencyDialogMock.commit(Mockito.any(Update.class))).thenReturn(toDoList);
        Mockito.when(bankDialogMock.commit(Mockito.any(Update.class))).thenReturn(toDoList);
        Mockito.when(startBalanceDialogMock.commit(Mockito.any(Update.class))).thenReturn(toDoList);
        Mockito.when(confirmDialogMock.commit(Mockito.any(Update.class))).thenReturn(toDoList);
        Mockito.when(saveDialogMock.commit(Mockito.any(Update.class))).thenReturn(toDoList);
    }

    @Test
    public void shouldProperlyExecuteStartDialogCommitAndTypeExecute() {
        //given
        int dialogStep = 0;
        DialogsState.removeAllDialogs(TEST_CHAT_ID);
        DialogsState.put(TEST_CHAT_ID, dialogName, TYPE.getName(), String.valueOf(TEST_TYPE_ACCOUNT_ID));
        DialogsState.put(TEST_CHAT_ID, dialogName, LAST_STEP.getId(), String.valueOf(dialogStep));
        Update update = getCallbackUpdate(String.format(accountCallbackPattern, "from", START.getName(), "start"));

        //when
        accountDialog.execute(update);

        //then
        Mockito.verify(startDialogMock).commit(update);
        Mockito.verify(typeDialogMock).execute(update);
    }

    @ParameterizedTest
    @MethodSource("sourceCommitExecute")
    public void shouldProperlyExecuteCommitExecute(Update update, int dialogStep, AccountNames addAccountName,
                                                   Dialog commitDialogMock, Dialog executeDialogMock) {
        //given
        DialogsState.removeAllDialogs(TEST_CHAT_ID);
        DialogsState.put(TEST_CHAT_ID, dialogName, TYPE.getName(), String.valueOf(TEST_TYPE_ACCOUNT_ID));
        DialogsState.put(TEST_CHAT_ID, dialogName, CURRENT_DIALOG_STEP.getId(), String.valueOf(addAccountName.ordinal()));
        DialogsState.put(TEST_CHAT_ID, dialogName, LAST_STEP.getId(), String.valueOf(dialogStep));

        //when
        System.out.println("EXPECTED COMMIT: currentStep " + dialogStep + " | " + commitDialogMock);
        System.out.println("EXPECTED EXECUTE: lastStep " + (dialogStep + 1) + " | " + executeDialogMock);
        accountDialog.execute(update);

        //then
        Mockito.verify(commitDialogMock).commit(update);
        Mockito.verify(executeDialogMock).execute(update);
    }

    private static Stream<Arguments> sourceCommitExecute() {
        List<Arguments> arguments = new ArrayList<>();
        for (int i = FIRST_STEP_INDEX.ordinal() + 1; i + 1 < AccountNames.values().length - 1; i++) {
            arguments.add(Arguments.of(getCallbackUpdate(AccountNames.values()[i]), i, AccountNames.values()[i],
                    commandContainerMock.retrieve(AccountNames.values()[i].getName()),
                    commandContainerMock.retrieve(AccountNames.values()[i + 1].getName())));
        }
        return arguments.stream();
    }

    private static Update getCallbackUpdate(AccountNames addAccountName) {
        return getCallbackUpdate(String.format(accountCallbackPattern, ADD_ACCOUNT.getName(),
                addAccountName.getName(), TEST_ADD_ID));
    }


    @Test
    public void shouldProperlyExecuteWithExecuteMode() {
        // TODO: Write it!
    }
}
