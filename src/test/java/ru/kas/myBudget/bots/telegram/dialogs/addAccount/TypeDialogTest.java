package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.AbstractDialogImplTest;
import ru.kas.myBudget.bots.telegram.dialogs.CommandDialogNames;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.keyboards.addAccountDialog.TypeKeyboard;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.texts.addAccountDialog.AddAccountText;
import ru.kas.myBudget.models.AccountType;
import ru.kas.myBudget.services.AccountTypeService;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.kas.myBudget.bots.telegram.callbacks.CallbackType.DIALOG;
import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.TYPE;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;


@DisplayName("Unit-level testing for AddAccount.TypeDialog")
public class TypeDialogTest extends AbstractDialogImplTest {
    private final static String TEST_EXISTENT_TYPE_ID = "7890";
    private final static String TEST_NONEXISTENT_TYPE_ID = "1111";
    private final static String CALLBACK_DATA_PATTERN = "%s_%s_%s_%s_%s";
    private final static String TEST_DATA_WITH_EXISTENT_TYPE_ID = String.format(CALLBACK_DATA_PATTERN,
            DIALOG.getId(), ADD_ACCOUNT.getName(), ADD_ACCOUNT.getName(), TYPE.getName(),
            TEST_EXISTENT_TYPE_ID);
    private final static String TEST_DATA_WITH_NONEXISTENT_TYPE_ID = String.format(CALLBACK_DATA_PATTERN,
            DIALOG.getId(), ADD_ACCOUNT.getName(), ADD_ACCOUNT.getName(), TYPE.getName(),
            TEST_NONEXISTENT_TYPE_ID);
    protected final AccountTypeService accountTypeServiceMock = Mockito.mock(AccountTypeService.class);
    protected final TypeKeyboard typeKeyboardMock = Mockito.mock(TypeKeyboard.class);
    private final static AccountType accountTypeMock = Mockito.mock(AccountType.class);

    @Override
    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
    }

    @Override
    protected String getCommandName() {
        return TYPE.getName();
    }

    @Override
    public CommandDialogNames getCommandDialogName() {
        return TYPE;
    }

    @Override
    public Dialog getCommand() {
        return new TypeDialog(botMessageServiceMock, telegramUserServiceMock, messageTextMock,
                typeKeyboardMock, dialogsMapMock, accountTypeServiceMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(AddAccountText.class);
    }

    @Override
    public void shouldReturnTrueByExecuteCommit(Update update) {
    }

    @Test
    public void shouldProperlySetAccountTypeServiceToKeyboard() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(typeKeyboardMock).setAccountTypeService(accountTypeServiceMock);
    }

    @ParameterizedTest
    @MethodSource("sourceCurrencyCommit")
    public void shouldProperlyExecuteCommit(Update update, int testCurrencyId,
                                            Optional<AccountType> returnFindById, boolean expected) {
        //given
        Mockito.when(accountTypeServiceMock.findById(testCurrencyId)).thenReturn(returnFindById);
        int times = expected ? 1 : 0;

        //when
        boolean result = getCommand().commit(update);

        //then
        assertEquals(expected, result);
        Mockito.verify(telegramUserServiceMock, Mockito.times(times)).checkUser(telegramUserServiceMock, update);
    }

    public static Stream<Arguments> sourceCurrencyCommit() {
        return Stream.of(
                Arguments.of(getUpdateWithText(TEST_TEXT),
                        TEST_EXISTENT_TYPE_ID, Optional.of(accountTypeMock), false),
                Arguments.of(getUpdateWithText(TEST_TEXT),
                        TEST_NONEXISTENT_TYPE_ID, Optional.empty(), false),

                Arguments.of(getUpdateWithText(TEST_COMMAND),
                        TEST_EXISTENT_TYPE_ID, Optional.of(accountTypeMock), false),
                Arguments.of(getUpdateWithText(TEST_COMMAND),
                        TEST_NONEXISTENT_TYPE_ID, Optional.empty(), false),

                Arguments.of(getUpdateWithText(TEST_DATA_WITH_EXISTENT_TYPE_ID),
                        TEST_EXISTENT_TYPE_ID, Optional.of(accountTypeMock), false),
                Arguments.of(getUpdateWithText(TEST_DATA_WITH_NONEXISTENT_TYPE_ID),
                        TEST_NONEXISTENT_TYPE_ID, Optional.empty(), false),

                Arguments.of(getCallbackUpdateWithData(TEST_TEXT),
                        TEST_EXISTENT_TYPE_ID, Optional.of(accountTypeMock), false),
                Arguments.of(getCallbackUpdateWithData(TEST_TEXT),
                        TEST_NONEXISTENT_TYPE_ID, Optional.empty(), false),

                Arguments.of(getCallbackUpdateWithData(TEST_COMMAND),
                        TEST_EXISTENT_TYPE_ID, Optional.of(accountTypeMock), false),
                Arguments.of(getCallbackUpdateWithData(TEST_COMMAND),
                        TEST_NONEXISTENT_TYPE_ID, Optional.empty(), false),

                Arguments.of(getCallbackUpdateWithData(TEST_DATA),
                        TEST_EXISTENT_TYPE_ID, Optional.of(accountTypeMock), false),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA),
                        TEST_NONEXISTENT_TYPE_ID, Optional.empty(), false),

                Arguments.of(getCallbackUpdateWithData(TEST_DATA_WITH_EXISTENT_TYPE_ID),
                        TEST_EXISTENT_TYPE_ID, Optional.of(accountTypeMock), true),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA_WITH_NONEXISTENT_TYPE_ID),
                        TEST_NONEXISTENT_TYPE_ID, Optional.empty(), false)
        );
    }
}
