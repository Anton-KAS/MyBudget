package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

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
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.FIRST_STEP_INDEX;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;

@DisplayName("Unit-level testing for AddAccount.AddAccountDialog")
public class AddAccountDialogTest extends AbstractMainDialogImplTest {
    private final static int TEST_ADD_ID = 666;

    private static String ADD_ACCOUNT_CALLBACK_PATTERN;

    private AddAccountDialog addAccountDialog;

    private final static AddAccountContainer addAccountContainerMock = Mockito.mock(AddAccountContainer.class);

    private final static AddAccountStartDialog START_ADD_ACCOUNT_DIALOG_MOCK = Mockito.mock(AddAccountStartDialog.class);
    private final static TypeDialog typeDialogMock = Mockito.mock(TypeDialog.class);
    private final static TitleDialog titleDialogMock = Mockito.mock(TitleDialog.class);
    private final static DescriptionDialog descriptionDialogMock = Mockito.mock(DescriptionDialog.class);
    private final static CurrencyDialog currencyDialogMock = Mockito.mock(CurrencyDialog.class);
    private final static BankDialog bankDialogMock = Mockito.mock(BankDialog.class);
    private final static StartBalanceDialog startBalanceDialogMock = Mockito.mock(StartBalanceDialog.class);
    private final static AddAccountConfirmDialog CONFIRM_ADD_ACCOUNT_DIALOG_MOCK = Mockito.mock(AddAccountConfirmDialog.class);
    private final static AddAccountSaveDialog ADD_ACCOUNT_SAVE_DIALOG_MOCK = Mockito.mock(AddAccountSaveDialog.class);


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

        this.addAccountDialog = new AddAccountDialog(botMessageServiceMock, telegramUserServiceMock, dialogsMapMock,
                addAccountContainerMock);
    }

    @Test
    public void shouldProperlyExecuteStartDialogCommitAndTypeExecute() {
        //given
        dialogMap = null;
        int dialogStep = 0;
        Mockito.when(dialogsMapMock.getDialogMapById(TEST_CHAT_ID)).thenReturn(dialogMap);
        Mockito.when(dialogsMapMock.getDialogStepById(TEST_CHAT_ID, TYPE.getName())).thenReturn(String.valueOf(dialogStep));
        Update update = getCallbackUpdate(String.format(ADD_ACCOUNT_CALLBACK_PATTERN, "from", START.getName(), "start"));

        //when
        addAccountDialog.execute(update);

        //then
        Mockito.verify(START_ADD_ACCOUNT_DIALOG_MOCK).commit(update);
        Mockito.verify(typeDialogMock).execute(update);
    }

    @ParameterizedTest
    @MethodSource("sourceCommitExecute")
    public void shouldProperlyExecuteCommitExecute(Update update, int dialogStep, AddAccountNames addAccountName,
                                                   Dialog commitDialogMock, Dialog executeDialogMock) {


        //given
        dialogMap = new HashMap<>();
        dialogMap.put(CURRENT_DIALOG_STEP.getId(), String.valueOf(dialogStep));
        dialogMap.put(LAST_STEP.getId(), String.valueOf(dialogStep));
        dialogMap.put(TYPE.getName(), "TEST TYPE ID");
        Mockito.when(dialogsMapMock.getDialogMapById(TEST_CHAT_ID)).thenReturn(dialogMap);
        Mockito.when(dialogsMapMock.getDialogStepById(TEST_CHAT_ID, addAccountName.getName())).thenReturn(String.valueOf(dialogStep));

        //when
        addAccountDialog.execute(update);

        //then
        Mockito.verify(commitDialogMock).commit(update);
        Mockito.verify(executeDialogMock).execute(update);
    }

    private static Stream<Arguments> sourceCommitExecute() {
        //int dialogStep = FIRST_STEP_INDEX.getIndex() + 1;
        List<Arguments> arguments = new ArrayList<>();
        for (int i = FIRST_STEP_INDEX.getIndex() + 1; i + 1 < AddAccountNames.values().length; i++) {
            arguments.add(Arguments.of(getCallbackUpdate(AddAccountNames.values()[i]), i, AddAccountNames.values()[i],
                    addAccountContainerMock.retrieve(AddAccountNames.values()[i].getName()),
                    addAccountContainerMock.retrieve(AddAccountNames.values()[i+1].getName())));
        }
        return arguments.stream();
    }

    private static Update getCallbackUpdate(AddAccountNames addAccountName) {
        return getCallbackUpdate(String.format(ADD_ACCOUNT_CALLBACK_PATTERN, ADD_ACCOUNT.getName(),
                addAccountName.getName(), TEST_ADD_ID));
    }


    @Test
    public void shouldProperlyExecuteWithExecuteMode() {
        // TODO: Write it!
    }
}
