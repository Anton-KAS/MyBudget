package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountContainer;
import ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountDialog;
import ru.kas.myBudget.bots.telegram.dialogs.AddAccount.StartDialog;
import ru.kas.myBudget.bots.telegram.dialogs.AddAccount.TypeDialog;
import ru.kas.myBudget.bots.telegram.dialogs.AddAccount.TitleDialog;
import ru.kas.myBudget.bots.telegram.dialogs.AddAccount.DescriptionDialog;
import ru.kas.myBudget.bots.telegram.dialogs.AddAccount.CurrencyDialog;
import ru.kas.myBudget.bots.telegram.dialogs.AddAccount.BankDialog;
import ru.kas.myBudget.bots.telegram.dialogs.AddAccount.SaveDialog;
import ru.kas.myBudget.bots.telegram.dialogs.AddAccount.StartBalanceDialog;
import ru.kas.myBudget.bots.telegram.dialogs.AbstractMainDialogImplTest;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountNames.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;

@DisplayName("Unit-level testing for AddAccount.AddAccountDialog")
public class AddAccountDialogTest extends AbstractMainDialogImplTest {
    private static String ADD_ACCOUNT_CALLBACK_PATTERN;

    private AddAccountDialog addAccountDialog;

    private final static AddAccountContainer addAccountContainerMock = Mockito.mock(AddAccountContainer.class);

    private final static StartDialog startDialogMock = Mockito.mock(StartDialog.class);
    private final static TypeDialog typeDialogMock = Mockito.mock(TypeDialog.class);
    private final static TitleDialog titleDialogMock = Mockito.mock(TitleDialog.class);
    private final static DescriptionDialog descriptionDialogMock = Mockito.mock(DescriptionDialog.class);
    private final static CurrencyDialog currencyDialogMock = Mockito.mock(CurrencyDialog.class);
    private final static BankDialog bankDialogMock = Mockito.mock(BankDialog.class);
    private final static StartBalanceDialog startBalanceDialogMock = Mockito.mock(StartBalanceDialog.class);
    private final static SaveDialog saveDialog = Mockito.mock(SaveDialog.class);


    @BeforeAll
    public static void beforeAll() {
        ADD_ACCOUNT_CALLBACK_PATTERN = String.format(CALLBACK_DIALOG_PATTERN, "%s", ADD_ACCOUNT.getName(), "%s", "%s");

        Mockito.when(addAccountContainerMock.retrieve(START.getName())).thenReturn(startDialogMock);
        Mockito.when(addAccountContainerMock.retrieve(TYPE.getName())).thenReturn(typeDialogMock);
        Mockito.when(addAccountContainerMock.retrieve(TITLE.getName())).thenReturn(titleDialogMock);
        Mockito.when(addAccountContainerMock.retrieve(DESCRIPTION.getName())).thenReturn(descriptionDialogMock);
        Mockito.when(addAccountContainerMock.retrieve(CURRENCY.getName())).thenReturn(currencyDialogMock);
        Mockito.when(addAccountContainerMock.retrieve(BANK.getName())).thenReturn(bankDialogMock);
        Mockito.when(addAccountContainerMock.retrieve(START_BALANCE.getName())).thenReturn(startBalanceDialogMock);
        Mockito.when(addAccountContainerMock.retrieve(SAVE.getName())).thenReturn(saveDialog);
    }

    @Override
    @BeforeEach
    public void beforeEach() {
        super.beforeEach();

        this.addAccountDialog = new AddAccountDialog(botMessageServiceMock, telegramUserServiceMock, dialogsMapMock,
                addAccountContainerMock);
    }

    @Test
    public void shouldProperlyExecuteStartDialog() {
        //given
        dialogMap = null;
        Mockito.when(dialogsMapMock.getDialogMapById(TEST_CHAT_ID)).thenReturn(dialogMap);
        Mockito.when(dialogsMapMock.getDialogStepById(TEST_CHAT_ID, TYPE.getName())).thenReturn("1");
        Update update = getCallbackUpdate(String.format(ADD_ACCOUNT_CALLBACK_PATTERN, "", START.getName(), "start"));

        //when
        addAccountDialog.execute(update);

        //then
        Mockito.verify(startDialogMock).commit(update);
    }

    @Test
    public void shouldProperlyExecuteWithExecuteMode() {
        // TODO: Write it!
    }
}
